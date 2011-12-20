package me.vgv.common.web.dispatcher;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.vgv.common.web.dispatcher.http.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DispatchHandler implements Handler {

	private final Injector injector;
	private final DispatchConfiguration dispatchConfiguration;
	private final Cache cache;
	private final RequestThreadLocalContext requestContext;
	private final HttpServletRequestThreadLocalContext httpRequestContext;
	private final HttpServletResponseThreadLocalContext httpResponseContext;

	@Inject
	public DispatchHandler(Injector injector) {
		this.injector = injector;

		// вытащим остальные поля
		this.dispatchConfiguration = injector.getInstance(DispatchConfiguration.class);

		DispatchCacheConfiguration cacheConfiguration = injector.getInstance(DispatchCacheConfiguration.class);
		CacheManager cacheManager = injector.getInstance(CacheManager.class);
		this.cache = cacheManager.getCache(cacheConfiguration.getCacheName());

		this.requestContext = injector.getInstance(RequestThreadLocalContext.class);
		this.httpRequestContext = injector.getInstance(HttpServletRequestThreadLocalContext.class);
		this.httpResponseContext = injector.getInstance(HttpServletResponseThreadLocalContext.class);
	}

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		Request request = DispatchUtils.createRequest(httpServletRequest);

		httpRequestContext.set(httpServletRequest);
		httpResponseContext.set(httpServletResponse);
		requestContext.set(request);
		try {
			DispatchChain chain = getChain(request);

			if (chain == null) {
				httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
			} else {
				chain.handle(httpServletRequest, httpServletResponse);
			}
		} finally {
			requestContext.set(null);
			httpResponseContext.set(null);
			httpRequestContext.set(null);
		}
	}

	private DispatchChain getChain(Request request) {
		Element element = cache.get(new RequestCacheKey(request));
		if (element != null) {
			return (DispatchChain) element.getObjectValue();
		} else {
			DispatchChain chain = DispatchUtils.createDispatchChain(dispatchConfiguration, request, injector);
			if (chain != null) {
				cache.put(new Element(new RequestCacheKey(request), chain));
			}
			return chain;
		}
	}
}

