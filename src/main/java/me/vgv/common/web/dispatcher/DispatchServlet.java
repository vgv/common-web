package me.vgv.common.web.dispatcher;

import com.google.inject.Injector;
import me.vgv.common.web.inject.InjectConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchServlet extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(DispatchServlet.class);

	private DispatchHandler dispatchHandler;

	@Override
	public void init() throws ServletException {
		try {
			// этот Injector сюда должен положить наш ServletContextListener
			Injector injector = (Injector) this.getServletContext().getAttribute(InjectConstants.SERVLET_CONTEXT_INJECTOR_NAME);
			if (injector != null) {
				log.info("Injector has been found in ServletContext");
				this.dispatchHandler = injector.getInstance(DispatchHandler.class);
				if (dispatchHandler == null) {
					log.info("DispatchHandler not found in Injector");
					throw new ServletException("DispatchHandler not found in Injector");
				}
			} else {
				log.info("Injector not found in ServletContext");
				throw new ServletException("Injector not found in ServletContext with attribute name " + InjectConstants.SERVLET_CONTEXT_INJECTOR_NAME);
			}
		} catch (Exception e) {
			throw new DispatchNestedException(e);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			dispatchHandler.handle(request, response);
		} catch (Exception e) {
			if (e instanceof ServletException) {
				throw (ServletException) e;
			} else if (e instanceof IOException) {
				throw (IOException) e;
			} else {
				throw new DispatchNestedException(e);
			}
		}
	}
}
