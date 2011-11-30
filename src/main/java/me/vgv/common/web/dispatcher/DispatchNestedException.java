package me.vgv.common.web.dispatcher;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchNestedException extends RuntimeException {

	public DispatchNestedException(Throwable cause) {
		super(cause);
	}

}
