package me.vgv.common.web.dispatcher;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public final class DispatchCacheConfiguration {

	private final String cacheName;

	public DispatchCacheConfiguration(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getCacheName() {
		return cacheName;
	}

}
