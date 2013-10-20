package me.vgv.common.web.cachemanager.provider;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import javax.servlet.ServletContext;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class ServletContextResourceProviderTest {

	@Test(groups = "unit")
	public void testGetResource() throws Exception {
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		ResourceProvider resourceProvider = new ServletContextResourceProvider(servletContext);
		resourceProvider.getResource("/some/test/file");

		Mockito.verify(servletContext).getResourceAsStream("/some/test/file");

	}
}
