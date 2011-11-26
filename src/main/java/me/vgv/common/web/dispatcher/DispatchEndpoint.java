package me.vgv.common.web.dispatcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import me.vgv.common.web.dispatcher.http.Request;
import me.vgv.common.web.dispatcher.pattern.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchEndpoint {

	// список шаблонов для этого обработчика
	private final List<Pattern<Request>> patterns;

	// это или interceptor или handler
	private final Object endpoint;

	private DispatchEndpoint(Object endpoint, List<Pattern<Request>> patterns) {
		Preconditions.checkArgument((endpoint instanceof Interceptor) || (endpoint instanceof Handler), "endpoint is not Interceptor or Handler");
		Preconditions.checkNotNull(patterns, "urlPattern is null");

		this.endpoint = endpoint;
		this.patterns = ImmutableList.copyOf(patterns);
	}

	public boolean match(Request request) {
		Preconditions.checkNotNull(request, "request is null");

		for (Pattern<Request> pattern : this.patterns) {
			if (pattern.match(request)) {
				return true;
			}
		}

		return false;
	}

	public Object getEndpoint() {
		return endpoint;
	}

	public static class Builder {

		private final Object endpoint;

		private List<Pattern<Request>> patterns = new ArrayList<Pattern<Request>>();

		public Builder(Object endpoint) {
			this.endpoint = endpoint;
		}

		public Builder forPattern(Pattern<Request> pattern) {
			this.patterns.add(pattern);
			return this;
		}

		public DispatchEndpoint build() {
			return new DispatchEndpoint(endpoint, patterns);
		}
	}

}
