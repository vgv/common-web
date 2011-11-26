package me.vgv.common.web.dispatcher.http;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public final class Request {

	private static final AtomicLong REQUEST_ID_GENERATOR = new AtomicLong(0L);

	private final long uniqueRequestId = REQUEST_ID_GENERATOR.incrementAndGet();
	private final long startTime = System.currentTimeMillis();

	private final String hostName;
	private final HttpMethod httpMethod;
	private final HttpSchema httpSchema;
	private final String uri;

	public Request(String hostName, HttpMethod httpMethod, HttpSchema httpSchema, String uri) {
		this.hostName = hostName;
		this.httpMethod = httpMethod;
		this.httpSchema = httpSchema;
		this.uri = uri;
	}

	public long getUniqueRequestId() {
		return uniqueRequestId;
	}

	public long getStartTime() {
		return startTime;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public HttpSchema getHttpSchema() {
		return httpSchema;
	}

	public String getHostName() {
		return hostName;
	}

	public String getUri() {
		return uri;
	}

	public boolean equalsHttpInfo(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Request request = (Request) o;

		if (hostName != null ? !hostName.equals(request.hostName) : request.hostName != null) return false;
		if (httpMethod != request.httpMethod) return false;
		if (httpSchema != request.getHttpSchema()) return false;
		if (uri != null ? !uri.equals(request.uri) : request.uri != null) return false;

		return true;
	}

	public int hashCodeHttpInfo() {
		int result = (hostName != null ? hostName.hashCode() : 0);
		result = 31 * result + (httpMethod != null ? httpMethod.hashCode() : 0);
		result = 31 * result + (httpSchema != null ? httpSchema.hashCode() : 0);
		result = 31 * result + (uri != null ? uri.hashCode() : 0);
		return result;
	}
}
