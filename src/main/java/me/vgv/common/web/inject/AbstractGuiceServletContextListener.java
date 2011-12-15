package me.vgv.common.web.inject;

import com.google.inject.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public abstract class AbstractGuiceServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// получим servlet context
		final ServletContext servletContext = sce.getServletContext();

		// создадим Injector
		List<Module> applicationModules = new ArrayList<Module>(getModules());
		// создадим модуль с servlet-context'ом чтобы потом можно было его инжектить куда-нибудь
		applicationModules.add(new AbstractModule() {
			@Override
			protected void configure() {
				bind(ServletContext.class).toInstance(servletContext);
			}
		});
		Injector injector = Guice.createInjector(getStage(), applicationModules);

		// запихнем этот injector в servlet-context чтобы сервлеты могли получить Injector
		servletContext.setAttribute(InjectConstants.SERVLET_CONTEXT_INJECTOR_NAME, injector);

		// вызовем callback
		onInjectorCreated(injector);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	public abstract List<Module> getModules();

	public void onInjectorCreated(Injector injector) {
		// NOP
	}

	public Stage getStage() {
		return Stage.PRODUCTION;
	}
}
