package me.vgv.common.web.dispatcher;

import com.google.inject.Inject;
import me.vgv.common.web.dispatcher.http.Request;
import me.vgv.common.web.dispatcher.http.RequestThreadLocalContext;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DispatchHandler implements Handler {

	private final DispatchConfiguration dispatchConfiguration;
	private final Cache cache;
	private final RequestThreadLocalContext requestThreadLocalContext;

	@Inject
	public DispatchHandler(DispatchConfiguration dispatchConfiguration, DispatchCacheConfiguration cacheConfiguration, CacheManager cacheManager, RequestThreadLocalContext requestThreadLocalContext) {
		this.dispatchConfiguration = dispatchConfiguration;
		this.cache = cacheManager.getCache(cacheConfiguration.getCacheName());
		this.requestThreadLocalContext = requestThreadLocalContext;
	}

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
		Request request = DispatchUtils.createRequest(httpServletRequest);

		requestThreadLocalContext.set(request);
		try {
			DispatchChain chain = getChain(request);

			if (chain == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
			} else {

			}
		} finally {
			requestThreadLocalContext.set(null);
		}
	}


	private DispatchChain getChain(Request request) {
		Element element = cache.get(new RequestKey(request));
		if (element != null) {
			return (DispatchChain) element.getObjectValue();
		} else {
			DispatchChain chain = DispatchUtils.createDispatchChain(dispatchConfiguration, request);
			if (chain != null) {
				cache.put(new Element(new RequestKey(request), chain));
			}
			return chain;
		}
	}
}

final class RequestKey {

	private final Request request;

	RequestKey(Request request) {
		this.request = request;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RequestKey that = (RequestKey) o;

		if (request != null ? !request.equalsHttpInfo(that.request) : that.request != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return request != null ? request.hashCodeHttpInfo() : 0;
	}

}