package me.vgv.common.web.dispatcher.pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class MatchAnyStringPatternTest {

	@Test(groups = "unit")
	public void testMatch() throws Exception {
		final Random rnd = new Random();
		MatchAnyStringPattern anyStringPattern = new MatchAnyStringPattern();

		for (int i = 0; i < 10000; i++) {
			Assert.assertTrue(anyStringPattern.match(RandomStringUtils.random(rnd.nextInt(20) + 4)));
		}
	}

}
