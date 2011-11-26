package me.vgv.common.web.dispatcher.pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public class StrictStringPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		Pattern<String> pattern = new StrictStringPattern("/index.html");
		Assert.assertTrue(pattern.match("/index.html"));
		Assert.assertFalse(pattern.match("/test/index.html"));
	}
}
