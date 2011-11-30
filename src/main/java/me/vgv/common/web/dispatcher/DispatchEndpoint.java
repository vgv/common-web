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
	private final Class<? extends Service> endpoint;

	public DispatchEndpoint(Class<? extends Service> endpoint, List<Pattern<Request>> patterns) {
		Preconditions.checkNotNull(endpoint, "endpoint is null");
		Preconditions.checkNotNull(patterns, "patterns is null");

		this.endpoint = endpoint;
		this.patterns = ImmutableList.copyOf(patterns);
	}

	public DispatchEndpoint(Class<? extends Service> endpoint, Pattern<Request> pattern) {
		Preconditions.checkNotNull(endpoint, "endpoint is null");
		Preconditions.checkNotNull(pattern, "pattern is null");

		this.endpoint = endpoint;
		this.patterns = ImmutableList.of(pattern);
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

	public Class<? extends Service> getEndpoint() {
		return endpoint;
	}

	public static class Builder {

		private final Class<? extends Service> endpoint;

		private List<Pattern<Request>> patterns = new ArrayList<Pattern<Request>>();

		public Builder(Class<? extends Service> endpoint) {
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
