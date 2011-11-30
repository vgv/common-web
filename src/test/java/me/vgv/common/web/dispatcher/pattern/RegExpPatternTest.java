package me.vgv.common.web.dispatcher.pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class RegExpPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		RegExpPattern pattern = new RegExpPattern("/catalog/[0-9]+/page");

		Assert.assertTrue(pattern.match("/catalog/123/page"));
		Assert.assertFalse(pattern.match("/catalog/newpage/page"));
	}
}
