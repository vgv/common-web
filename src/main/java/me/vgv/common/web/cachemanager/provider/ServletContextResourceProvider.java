package me.vgv.common.web.cachemanager.provider;

import javax.servlet.ServletContext;
import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ServletContextResourceProvider implements ResourceProvider {

	private final ServletContext servletContext;

	public ServletContextResourceProvider(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public InputStream getResource(String resourceName) {
		return servletContext.getResourceAsStream(resourceName);
	}
}
