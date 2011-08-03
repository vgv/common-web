package me.vgv.common.web.jsp;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface ViewResolver {

	public String getJspPath(String viewName);

}
