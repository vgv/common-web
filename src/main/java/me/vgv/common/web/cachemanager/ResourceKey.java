package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceKey {

	private final String resourceName;
	private final boolean gzipped;
	private final String etag;

	public ResourceKey(String resourceName, boolean gzipped, String etag) {
		Preconditions.checkNotNull(resourceName, "resourceName is null");

		this.resourceName = resourceName;
		this.gzipped = gzipped;
		this.etag = etag;
	}

	public String getResourceName() {
		return resourceName;
	}

	public boolean isGzipped() {
		return gzipped;
	}

	public String getEtag() {
		return etag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ResourceKey that = (ResourceKey) o;

		if (gzipped != that.gzipped) return false;
		if (etag != null ? !etag.equals(that.etag) : that.etag != null) return false;
		if (resourceName != null ? !resourceName.equals(that.resourceName) : that.resourceName != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = resourceName != null ? resourceName.hashCode() : 0;
		result = 31 * result + (gzipped ? 1 : 0);
		result = 31 * result + (etag != null ? etag.hashCode() : 0);
		return result;
	}
}
