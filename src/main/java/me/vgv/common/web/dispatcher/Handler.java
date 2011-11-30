package me.vgv.common.web.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface Handler extends Service {

	public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
