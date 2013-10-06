package me.vgv.common.web.dispatcher;

import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;
import me.vgv.common.web.dispatcher.pattern.Patterns;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DispatchEndpointTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		DispatchEndpoint dispatchEndpoint = new DispatchEndpoint.Builder(Interceptor.class).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.matchPrefix("/catalog1/")).build()).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.matchSuffix(".jsp")).build()).
				forPattern(Patterns.matchRequestBuilder().forUri(Patterns.matchExact("/login")).build()).build();

		Assert.assertTrue(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/login")));
		Assert.assertFalse(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/login/")));

		Assert.assertTrue(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/catalog/subcatalog/page.jsp")));
		Assert.assertFalse(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/catalog/subcatalog/page.jsp/index.html")));

		Assert.assertTrue(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/catalog1/index")));
		Assert.assertFalse(dispatchEndpoint.match(new Request("localhost", HttpMethod.GET, HttpSchema.HTTP, "/catalog1")));
	}

}
