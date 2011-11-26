package me.vgv.common.web.dispatcher.http;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public enum HttpMethod {

	GET,

	POST,

	HEAD,

	PUT;

	public static HttpMethod findByString(String httpMethod) {
		Preconditions.checkNotNull(httpMethod, "httpMethod is null");
		httpMethod = httpMethod.trim().toLowerCase();

		for (HttpMethod method : values()) {
			if (httpMethod.equals(method.name().toLowerCase())) {
				return method;
			}
		}

		return null;
	}

}
