package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class StrictStringPattern implements Pattern<String> {

	private final String pattern;

	public StrictStringPattern(String pattern) {
		Preconditions.checkNotNull(pattern, "pattern is null");
		this.pattern = pattern;
	}

	@Override
	public boolean match(String value) {
		return pattern.equals(value);
	}
}
