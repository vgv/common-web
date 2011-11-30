package me.vgv.common.web.dispatcher.http;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public enum HttpMethod {

	GET,

	POST,

	HEAD,

	PUT;

	public static HttpMethod findByString(String httpMethod) {
		Preconditions.checkNotNull(httpMethod, "httpMethod is null");
		httpMethod = httpMethod.trim().toUpperCase();

		for (HttpMethod method : values()) {
			if (httpMethod.equals(method.name().toUpperCase())) {
				return method;
			}
		}

		return null;
	}

}
