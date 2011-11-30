package me.vgv.common.web.dispatcher.pattern;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public interface Pattern<T> {

	boolean match(T value);

}
