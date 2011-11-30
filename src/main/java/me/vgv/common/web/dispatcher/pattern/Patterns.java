package me.vgv.common.web.dispatcher.pattern;

import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class Patterns {

	private static final MatchAnyStringPattern MATCH_ANY_STRING_PATTERN = new MatchAnyStringPattern();

	// string matchers
	public static Pattern<String> matchAny() {
		return MATCH_ANY_STRING_PATTERN;
	}

	public static Pattern<String> matchPrefix(String prefix) {
		return new PrefixStringPattern(prefix);
	}

	public static Pattern<String> matchSuffix(String suffix) {
		return new SuffixStringPattern(suffix);
	}

	public static Pattern<String> matchExact(String strict) {
		return new StrictStringPattern(strict);
	}

	public static Pattern<String> matchRegexp(String regexp) {
		return new RegExpPattern(regexp);
	}

	// request matchers
	public static Pattern<Request> matchRequest(Pattern<String> hostNamePattern, Set<HttpMethod> httpMethods, Set<HttpSchema> httpSchemas, Pattern<String> uriPattern) {
		return new EndpointPattern(hostNamePattern, httpMethods, httpSchemas, uriPattern);
	}

	public static Pattern<Request> matchRequest(Pattern<String> uriPattern) {
		return new EndpointPattern(Patterns.matchAny(), EnumSet.allOf(HttpMethod.class), EnumSet.allOf(HttpSchema.class), uriPattern);
	}

	public static EndpointPattern.Builder matchRequestBuilder() {
		return new EndpointPattern.Builder();
	}
}
