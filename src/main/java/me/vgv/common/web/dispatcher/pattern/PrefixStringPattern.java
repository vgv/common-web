package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class PrefixStringPattern implements Pattern<String> {

	private final String prefix;

	public PrefixStringPattern(String prefix) {
		Preconditions.checkNotNull(prefix, "prefix is null");
		this.prefix = prefix;
	}

	@Override
	public boolean match(String value) {
		return value.startsWith(prefix);
	}
}
