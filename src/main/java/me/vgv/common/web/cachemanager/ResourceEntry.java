package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceEntry {

	private final byte[] data;
	private final String etag;
	private final ResourceCacheMode cacheMode;
	private final String mimeType;

	public ResourceEntry(byte[] data, String etag, ResourceCacheMode cacheMode, String mimeType) {
		Preconditions.checkNotNull(data, "data is not null");
		Preconditions.checkNotNull(cacheMode, "cacheMode is not null");

		this.data = data;
		this.etag = etag;
		this.cacheMode = cacheMode;
		this.mimeType = mimeType;
	}

	public byte[] getData() {
		return data;
	}

	public String getEtag() {
		return etag;
	}

	public ResourceCacheMode getCacheMode() {
		return cacheMode;
	}

	public String getMimeType() {
		return mimeType;
	}
}
