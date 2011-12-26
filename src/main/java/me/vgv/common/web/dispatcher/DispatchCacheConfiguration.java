package me.vgv.common.web.dispatcher;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchCacheConfiguration {

	private final String cacheName;

	public DispatchCacheConfiguration(String cacheName) {
		Preconditions.checkNotNull(cacheName, "cacheName is null");
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return cacheName;
	}

}
