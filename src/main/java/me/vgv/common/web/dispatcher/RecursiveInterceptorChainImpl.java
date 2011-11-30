package me.vgv.common.web.dispatcher;

import com.google.common.base.Preconditions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class RecursiveInterceptorChainImpl implements InterceptorChain {

	private final List<Interceptor> interceptors;
	private final Handler handler;

	public RecursiveInterceptorChainImpl(List<Interceptor> interceptors, Handler handler) {
		Preconditions.checkNotNull(interceptors, "interceptors is null");
		Preconditions.checkNotNull(handler, "handler is null");
		this.interceptors = interceptors;
		this.handler = handler;
	}

	@Override
	public void callNext(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (interceptors.isEmpty()) {
			// интерцепторов больше нет, вызовем сам обработчик
			handler.handle(request, response);
		} else {
			// в списке еще есть интерцепторы, пойдем дальше
			Interceptor currentInterceptor = interceptors.get(0);
			List<Interceptor> subChain = interceptors.subList(1, interceptors.size());
			currentInterceptor.intercept(request, response, new RecursiveInterceptorChainImpl(subChain, handler));
		}
	}
}
