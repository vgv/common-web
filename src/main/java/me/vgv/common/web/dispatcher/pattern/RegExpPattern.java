package me.vgv.common.web.dispatcher.pattern;

import com.google.common.base.Preconditions;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class RegExpPattern implements Pattern<String> {

	private final java.util.regex.Pattern pattern;

	public RegExpPattern(String pattern) {
		Preconditions.checkNotNull(pattern, "pattern is null");
		this.pattern = java.util.regex.Pattern.compile(pattern);
	}

	@Override
	public boolean match(String value) {
		return pattern.matcher(value).matches();
	}

}
