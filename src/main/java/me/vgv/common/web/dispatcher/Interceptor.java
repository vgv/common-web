package me.vgv.common.web.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface Interceptor {

	public void intercept(HttpServletRequest request, HttpServletResponse response, InterceptorChain interceptorChain) throws Exception;

}
