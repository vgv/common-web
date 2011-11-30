package me.vgv.common.web.dispatcher.http;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class RequestTest {

	@Test(groups = "unit")
	public void testEqualsHttpInfo() throws Exception {
		Request request1 = new Request("hostname", HttpMethod.GET, HttpSchema.HTTP, "uri");
		Thread.sleep(50);
		Request request2 = new Request("hostname", HttpMethod.GET, HttpSchema.HTTP, "uri");

		Assert.assertTrue(request1.getUniqueRequestId() != request2.getUniqueRequestId());
		Assert.assertNotEquals(request1, request2);
		Assert.assertTrue(request1.equalsHttpInfo(request2));
		Assert.assertEquals(request1.hashCodeHttpInfo(), request2.hashCodeHttpInfo());
	}
}
