package me.vgv.common.web.dispatcher;

import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DispatchEndpointTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {/*
		DispatchEndpoint dispatchElement = new DispatchEndpoint.Builder(Mockito.mock(Interceptor.class)).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.matchPrefix("/catalog1/*")).build()).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.matchSuffix("*.jsp")).build()).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.match("/login")).build()).build();


		Assert.assertTrue(dispatchElement.match("/login"));
		Assert.assertFalse(dispatchElement.match("/login/"));

		Assert.assertTrue(dispatchElement.match("/catalog/subcatalog/page.jsp"));
		Assert.assertFalse(dispatchElement.match("/catalog/subcatalog/page.jsp/index.html"));

		Assert.assertTrue(dispatchElement.match("/catalog1/index"));
		Assert.assertFalse(dispatchElement.match("/catalog1"));*/
	}

	@Test(groups = "unit")
	public void testGetRequestProcessor() throws Exception {
		/*
		Interceptor interceptor = Mockito.mock(Interceptor.class);
		DispatchEndpoint dispatchElement = new DispatchEndpoint.Builder(interceptor).withSimplePattern("/catalog/*").build();

		Assert.assertEquals(interceptor, dispatchElement.getEndpoint());
		*/
	}
}
