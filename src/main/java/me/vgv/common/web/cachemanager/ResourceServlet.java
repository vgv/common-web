package me.vgv.common.web.cachemanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.vgv.common.web.WebDateTimeConstants;
import net.sf.ehcache.Ehcache;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;import java.lang.Override;import java.lang.String;import java.lang.System;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
@Singleton
public final class ResourceServlet extends HttpServlet {

	private final Ehcache cache;
	private ResourceManager resourceManager;

	@Inject
	public ResourceServlet(Ehcache cache) {
		this.cache = cache;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.resourceManager = new ResourceManager(config.getServletContext(), cache);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resourceName = request.getRequestURI().substring(request.getContextPath().length());

		boolean gzipped = false;
		String acceptEncodingHeader = request.getHeader("Accept-Encoding");
		if (acceptEncodingHeader != null && acceptEncodingHeader.contains("gzip")) {
			gzipped = true;
		}

		String etag = request.getHeader("If-None-Match");

		ResourceKey resourceKey = new ResourceKey(resourceName, gzipped, etag);
		ResourceEntry resourceEntry = resourceManager.getResource(resourceKey);

		if (resourceEntry == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			final long time = System.currentTimeMillis();

			// установим общие заголовки
			response.setCharacterEncoding("UTF-8");
			response.setDateHeader("Date", time);

			if (gzipped) {
				response.setHeader("Content-Encoding", "gzip");
			}

			switch (resourceEntry.getCacheMode()) {
				case CACHE_FOREVER: {
					response.setDateHeader("Last-Modified", WebDateTimeConstants.SOME_DAY_IN_PAST);
					response.setDateHeader("Expires", time + WebDateTimeConstants.ONE_YEAR_IN_MILLISECONDS);
					response.setHeader("Cache-control", "public, max-age=" + WebDateTimeConstants.ONE_YEAR_IN_SECONDS);
					response.setContentLength(resourceEntry.getData().length);
					response.getOutputStream().write(resourceEntry.getData());
					break;
				}
				case CACHE_NEVER: {
					response.setDateHeader("Expires", WebDateTimeConstants.SOME_DAY_IN_PAST);
					response.setHeader("Pragma", "no-cache");
					response.setHeader("Cache-control", "no-cache, must-revalidate");
					response.setContentLength(resourceEntry.getData().length);
					response.getOutputStream().write(resourceEntry.getData());
					break;
				}
				case CACHE_BY_ETAG: {
					if (resourceEntry.getEtag().equals(etag)) {
						response.setDateHeader("Expires", time + WebDateTimeConstants.ONE_MONTH_IN_MILLISECONDS);
						response.setHeader("ETag", resourceEntry.getEtag());
						response.setHeader("Cache-control", "max-age=" + WebDateTimeConstants.ONE_MONTH_IN_SECONDS);
						response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
					} else {
						response.setDateHeader("Last-Modified", WebDateTimeConstants.SOME_DAY_IN_PAST);
						response.setDateHeader("Expires", time + WebDateTimeConstants.ONE_MONTH_IN_MILLISECONDS);
						response.setHeader("ETag", resourceEntry.getEtag());
						response.setHeader("Cache-control", "public, max-age=" + WebDateTimeConstants.ONE_MONTH_IN_SECONDS);
						response.setContentLength(resourceEntry.getData().length);
						response.getOutputStream().write(resourceEntry.getData());
					}

					break;
				}
			}
		}
	}

}
