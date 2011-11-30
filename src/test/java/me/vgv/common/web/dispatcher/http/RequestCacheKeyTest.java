package me.vgv.common.web.dispatcher.http;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class RequestCacheKeyTest {

	@Test(groups = "unit")
	public void testEquals() throws Exception {
		Request request1 = new Request("hostname", HttpMethod.GET, HttpSchema.HTTP, "uri");
		Thread.sleep(50);
		Request request2 = new Request("hostname", HttpMethod.GET, HttpSchema.HTTP, "uri");

		Assert.assertTrue(request1.getUniqueRequestId() != request2.getUniqueRequestId());
		Assert.assertNotEquals(request1, request2);
		Assert.assertTrue(request1.equalsHttpInfo(request2));
		Assert.assertEquals(request1.hashCodeHttpInfo(), request2.hashCodeHttpInfo());

		// подготовим RequestCacheKey
		RequestCacheKey cacheKey1 = new RequestCacheKey(request1);
		RequestCacheKey cacheKey2 = new RequestCacheKey(request2);

		Assert.assertEquals(cacheKey1, cacheKey2);
		Assert.assertEquals(cacheKey1.hashCode(), cacheKey2.hashCode());
	}

}
