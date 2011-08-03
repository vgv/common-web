package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;

import java.io.Serializable;import java.lang.String;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private final byte[] data;
	private final String etag;
	private final CacheMode cacheMode;

	public ResourceEntry(byte[] data, String etag, CacheMode cacheMode) {
		Preconditions.checkNotNull(data, "data is not null");
		Preconditions.checkNotNull(cacheMode, "cacheMode is not null");

		this.data = data;
		this.etag = etag;
		this.cacheMode = cacheMode;
	}

	public byte[] getData() {
		return data;
	}

	public String getEtag() {
		return etag;
	}

	public CacheMode getCacheMode() {
		return cacheMode;
	}

}
