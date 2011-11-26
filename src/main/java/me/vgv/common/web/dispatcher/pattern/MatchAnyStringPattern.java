package me.vgv.common.web.dispatcher.pattern;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
final class MatchAnyStringPattern implements Pattern<String> {

	public MatchAnyStringPattern() {
	}

	@Override
	public boolean match(String value) {
		return true;
	}
}
