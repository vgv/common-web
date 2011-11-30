package me.vgv.common.web.dispatcher.http;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class RequestCacheKey {

	private final Request request;

	public RequestCacheKey(Request request) {
		this.request = request;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RequestCacheKey that = (RequestCacheKey) o;

		if (request != null ? !request.equalsHttpInfo(that.request) : that.request != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return request != null ? request.hashCodeHttpInfo() : 0;
	}
}
