package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
final class EndpointPattern implements Pattern<Request> {

	private final Pattern<String> hostNamePattern;
	private final Set<HttpMethod> httpMethods;
	private final Set<HttpSchema> httpSchemas;
	private final Pattern<String> uriPattern;

	public EndpointPattern(Pattern<String> hostNamePattern, Set<HttpMethod> httpMethods, Set<HttpSchema> httpSchemas, Pattern<String> uriPattern) {
		Preconditions.checkNotNull(hostNamePattern, "hostNamePattern is null");
		Preconditions.checkNotNull(httpMethods, "httpMethods is null");
		Preconditions.checkNotNull(httpSchemas, "httpSchemas is null");
		Preconditions.checkNotNull(uriPattern, "uriPattern is null");

		Preconditions.checkArgument(!httpMethods.isEmpty(), "httpMethods is empty");
		Preconditions.checkArgument(!httpSchemas.isEmpty(), "httpSchemas is empty");

		this.hostNamePattern = hostNamePattern;
		this.httpMethods = Sets.immutableEnumSet(httpMethods);
		this.httpSchemas = Sets.immutableEnumSet(httpSchemas);
		this.uriPattern = uriPattern;
	}

	@Override
	public boolean match(Request request) {
		if (!hostNamePattern.match(request.getHostName())) {
			return false;
		}

		if (!this.httpMethods.contains(request.getHttpMethod())) {
			return false;
		}

		if (!this.httpSchemas.contains(request.getHttpSchema())) {
			return false;
		}

		if (!this.uriPattern.match(request.getUri())) {
			return false;
		}

		return true;
	}

	public Pattern getHostNamePattern() {
		return hostNamePattern;
	}

	public Set<HttpMethod> getHttpMethods() {
		return httpMethods;
	}

	public Set<HttpSchema> getHttpSchemas() {
		return httpSchemas;
	}

	public Pattern getUriPattern() {
		return uriPattern;
	}

	public static class Builder {
		private Pattern<String> hostNamePattern;
		private Set<HttpMethod> httpMethods = EnumSet.noneOf(HttpMethod.class);
		private Set<HttpSchema> httpSchemas = EnumSet.noneOf(HttpSchema.class);
		private Pattern<String> uriPattern;

		public Builder forMethod(HttpMethod method) {
			this.httpMethods.add(method);
			return this;
		}

		public Builder forSchema(HttpSchema httpSchema) {
			this.httpSchemas.add(httpSchema);
			return this;
		}

		public Builder forHostName(Pattern<String> stringPattern) {
			this.hostNamePattern = stringPattern;
			return this;
		}

		public Builder forUri(Pattern<String> stringPattern) {
			this.uriPattern = stringPattern;
			return this;
		}

		public EndpointPattern build() {
			return new EndpointPattern(this.hostNamePattern, this.httpMethods, this.httpSchemas, this.uriPattern);
		}
	}
}
