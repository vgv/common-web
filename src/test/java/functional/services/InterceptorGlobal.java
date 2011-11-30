package functional.services;

import me.vgv.common.web.dispatcher.Interceptor;
import me.vgv.common.web.dispatcher.InterceptorChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class InterceptorGlobal implements Interceptor {

	@Override
	public void intercept(HttpServletRequest request, HttpServletResponse response, InterceptorChain interceptorChain) throws Exception {
		response.getWriter().write("interceptor_global");
		interceptorChain.callNext(request, response);
	}
}
