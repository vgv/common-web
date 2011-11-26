package me.vgv.common.web.dispatcher;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DispatchEndpointTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		DispatchEndpoint dispatchElement = new DispatchEndpoint.Builder(Mockito.mock(Interceptor.class)).
				withSimplePattern("/catalog1/*").withSimplePattern("*.jsp").withSimplePattern("/login").build();

		Assert.assertTrue(dispatchElement.match("/login"));
		Assert.assertFalse(dispatchElement.match("/login/"));

		Assert.assertTrue(dispatchElement.match("/catalog/subcatalog/page.jsp"));
		Assert.assertFalse(dispatchElement.match("/catalog/subcatalog/page.jsp/index.html"));

		Assert.assertTrue(dispatchElement.match("/catalog1/index"));
		Assert.assertFalse(dispatchElement.match("/catalog1"));
	}

	@Test(groups = "unit")
	public void testGetRequestProcessor() throws Exception {
		Interceptor interceptor = Mockito.mock(Interceptor.class);
		DispatchEndpoint dispatchElement = new DispatchEndpoint.Builder(interceptor).withSimplePattern("/catalog/*").build();

		Assert.assertEquals(interceptor, dispatchElement.getEndpoint());
	}
}
