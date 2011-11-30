package me.vgv.common.web.dispatcher.pattern;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
final class MatchAnyStringPattern implements Pattern<String> {

	public MatchAnyStringPattern() {
	}

	@Override
	public boolean match(String value) {
		return true;
	}
}
