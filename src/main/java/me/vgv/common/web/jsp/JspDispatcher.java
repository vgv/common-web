package me.vgv.common.web.jsp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface JspDispatcher {

	public void dispatch(final String jspName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
