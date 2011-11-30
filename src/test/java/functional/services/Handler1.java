package functional.services;

import me.vgv.common.web.dispatcher.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class Handler1 implements Handler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().write("handler1");
	}

}
