package me.vgv.common.web.dispatcher.pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public class PrefixStringPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		Pattern<String> pattern = new PrefixStringPattern("/catalog/");
		Assert.assertTrue(pattern.match("/catalog/page1"));
		Assert.assertFalse(pattern.match("/test/catalog/page1"));
	}
}
