package me.vgv.common.web.jsp;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class SimpleViewResolverTest {

	@Test(groups = "unit")
	public void testResolver() throws Exception {
		SimpleViewResolver simpleViewResolver = new SimpleViewResolver("before-", "-after");
		Assert.assertEquals("before-view-after", simpleViewResolver.getJspPath("view"));

		simpleViewResolver = new SimpleViewResolver("before-", null);
		Assert.assertEquals("before-view", simpleViewResolver.getJspPath("view"));

		simpleViewResolver = new SimpleViewResolver(null, "-after");
		Assert.assertEquals("view-after", simpleViewResolver.getJspPath("view"));
	}

}
