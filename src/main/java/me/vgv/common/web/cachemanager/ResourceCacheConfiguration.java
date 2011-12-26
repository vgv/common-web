package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceCacheConfiguration {

	private final String cacheName;

	public ResourceCacheConfiguration(String cacheName) {
		Preconditions.checkNotNull(cacheName, "cacheName is null");
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return cacheName;
	}
}
