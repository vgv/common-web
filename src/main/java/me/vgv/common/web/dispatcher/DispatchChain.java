package me.vgv.common.web.dispatcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchChain {

	private final List<Interceptor> interceptors;

	private final Handler handler;

	public DispatchChain(List<Interceptor> interceptors, Handler handler) {
		Preconditions.checkNotNull(interceptors, "interceptors is null");
		Preconditions.checkNotNull(handler, "handler is null");

		this.interceptors = ImmutableList.copyOf(interceptors);
		this.handler = handler;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public Handler getHandler() {
		return handler;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InterceptorChain interceptorChain = new RecursiveInterceptorChainImpl(interceptors, handler);
		interceptorChain.callNext(request, response);
	}
}
