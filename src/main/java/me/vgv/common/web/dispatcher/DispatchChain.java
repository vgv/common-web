package me.vgv.common.web.dispatcher;

import com.google.common.collect.ImmutableList;
import me.vgv.common.web.dispatcher.http.Request;

import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchChain {

	private final Request request;

	private final List<Interceptor> interceptors;

	private final Handler handler;

	public DispatchChain(Request request, List<Interceptor> interceptors, Handler handler) {
		this.request = request;
		this.interceptors = ImmutableList.copyOf(interceptors);
		this.handler = handler;
	}

	public Request getRequest() {
		return request;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public Handler getHandler() {
		return handler;
	}
}
