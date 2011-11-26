package me.vgv.common.web.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface InterceptorChain {

	public void callNext(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
