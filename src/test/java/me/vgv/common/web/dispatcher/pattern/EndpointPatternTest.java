package me.vgv.common.web.dispatcher.pattern;

import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class EndpointPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		EndpointPattern pattern = new EndpointPattern.Builder().
				forHostName(Patterns.matchExact("example.com")).
				forSchema(HttpSchema.HTTP).
				forMethod(HttpMethod.GET).
				forUri(Patterns.matchPrefix("/catalog/")).
				build();

		Assert.assertTrue(pattern.match(new Request("example.com", HttpMethod.GET, HttpSchema.HTTP, "/catalog/page")));
		Assert.assertFalse(pattern.match(new Request("example.com", HttpMethod.POST, HttpSchema.HTTP, "/catalog/page")));
		Assert.assertFalse(pattern.match(new Request("otherhost.com", HttpMethod.GET, HttpSchema.HTTP, "/catalog/page")));

	}
}
