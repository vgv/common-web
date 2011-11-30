package me.vgv.common.web.dispatcher.pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SuffixStringPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		Pattern<String> pattern = new SuffixStringPattern(".jsp");
		Assert.assertTrue(pattern.match("/catalog/page.jsp"));
		Assert.assertFalse(pattern.match("/test/catalog.jsp/page1"));
	}
}
