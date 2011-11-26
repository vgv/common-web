package me.vgv.common.web.dispatcher.pattern;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public interface Pattern<T> {

	boolean match(T value);

}
