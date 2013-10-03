package me.vgv.common.web.cachemanager.provider;

import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface ResourceProvider {

	public InputStream getResource(String resourceName);

}
