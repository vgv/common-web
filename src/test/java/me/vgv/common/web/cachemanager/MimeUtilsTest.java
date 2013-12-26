package me.vgv.common.web.cachemanager;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class MimeUtilsTest {

	@Test(groups = "unit")
	public void testGetMimeType() throws Exception {
		Assert.assertEquals(MimeUtils.getMimeType("a.css"), "text/css");
		Assert.assertEquals(MimeUtils.getMimeType("a.html"), "text/html");
		Assert.assertEquals(MimeUtils.getMimeType("a.js"), "application/javascript");
		Assert.assertEquals(MimeUtils.getMimeType("a.gif"), "image/gif");
		Assert.assertEquals(MimeUtils.getMimeType("a.eot"), "application/vnd.ms-fontobject");
		Assert.assertEquals(MimeUtils.getMimeType("a.woff"), "application/font-woff");
	}
}
