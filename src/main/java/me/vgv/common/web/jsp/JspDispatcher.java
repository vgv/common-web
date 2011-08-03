package me.vgv.common.web.jsp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class JspDispatcher {

	private final ViewResolver viewResolver;

	public JspDispatcher(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	/**
	 * Метод для решения проблем с Guice + Jetty + JSP
	 * У Guice+Jetty есть определенные проблемы с JSP, подробнее см. ссылку ниже.
	 * По-умолчанию, Guice не обрабатывает *.jsp запросы и поэтому их обрабатывает
	 * default jetty servlet, который правильно отдает JSP (компилирует и прочее).
	 * если же мы используем ResourceServlet, который перехватывает обращения ко всем
	 * ресурсам - управление до дефолтного JspServlet не доходит и получается фигня,
	 * поэтому надо сделать следующее:
	 * <pre>
	 * 1) забиндить руками JspServlet
	 * 2) делать специальную обработку редиректа на JSP-страницу через этот метод
	 * </pre>
	 *
	 * @param jspName  имя jsp-view, которое будет преобразовано в полный путь к jsp-файлу с помощью viewResolver
	 * @param request  HTTP-запрос
	 * @param response HTTP-ответ
	 * @throws java.io.IOException			просто транслируем исключения наверх
	 * @throws javax.servlet.ServletException просто транслируем исключения наверх
	 * @see <a href="http://code.google.com/p/google-guice/issues/detail?id=372">Guice Bug 372</a>
	 */
	public void dispatch(final String jspName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		final String jspPath = viewResolver.getJspPath(jspName);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(jspPath);
		requestDispatcher.include(new HttpServletRequestWrapper(request) {
			@Override
			public String getServletPath() {
				return jspPath;
			}
		}, response);
	}

}
