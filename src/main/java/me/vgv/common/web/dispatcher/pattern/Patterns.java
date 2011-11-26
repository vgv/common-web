package me.vgv.common.web.dispatcher.pattern;

import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;

import java.util.Set;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public final class Patterns {

	private static final MatchAnyStringPattern MATCH_ANY_STRING_PATTERN = new MatchAnyStringPattern();

	public static Pattern<String> matchAny() {
		return MATCH_ANY_STRING_PATTERN;
	}

	public static Pattern<String> matchPrefix(String prefix) {
		return new PrefixStringPattern(prefix);
	}

	public static Pattern<String> matchSuffix(String suffix) {
		return new SuffixStringPattern(suffix);
	}

	public static Pattern<String> match(String strict) {
		return new StrictStringPattern(strict);
	}

	public static Pattern<String> matchRegexp(String regexp) {
		return new RegExpPattern(regexp);
	}

	public static Pattern<Request> matchRequest(Pattern<String> hostNamePattern, Set<HttpMethod> httpMethods, Set<HttpSchema> httpSchemas, Pattern<String> uriPattern) {
		return new EndpointPattern(hostNamePattern, httpMethods, httpSchemas, uriPattern);
	}

	public static EndpointPattern.Builder matchRequestBuilder() {
		return new EndpointPattern.Builder();
	}
}
