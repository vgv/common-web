package me.vgv.common.web.cachemanager.provider;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ClassPathResourceProvider implements ResourceProvider {

	@Override
	public InputStream getResource(String resourceName) throws IOException {
		return getClass().getResourceAsStream(resourceName);
	}

}
