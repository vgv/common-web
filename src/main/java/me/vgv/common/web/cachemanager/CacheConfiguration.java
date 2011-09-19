package me.vgv.common.web.cachemanager;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public final class CacheConfiguration {

	private final String cacheName;

	public CacheConfiguration(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return cacheName;
	}
}
