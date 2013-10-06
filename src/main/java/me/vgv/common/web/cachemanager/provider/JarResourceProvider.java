package me.vgv.common.web.cachemanager.provider;

import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class JarResourceProvider implements ResourceProvider {

	@Override
	public InputStream getResource(String resourceName) {
		return getClass().getResourceAsStream(resourceName);
	}

}
