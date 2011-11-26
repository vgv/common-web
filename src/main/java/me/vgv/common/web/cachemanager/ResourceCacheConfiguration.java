package me.vgv.common.web.cachemanager;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceCacheConfiguration {

	private final String cacheName;

	public ResourceCacheConfiguration(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return cacheName;
	}
}
