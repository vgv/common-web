package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class SuffixStringPattern implements Pattern<String> {

	private final String suffix;

	public SuffixStringPattern(String suffix) {
		Preconditions.checkNotNull(suffix, "suffix is null");
		this.suffix = suffix;
	}

	@Override
	public boolean match(String value) {
		return value.endsWith(suffix);
	}
}
