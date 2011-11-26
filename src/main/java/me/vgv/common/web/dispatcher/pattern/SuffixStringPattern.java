package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
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
