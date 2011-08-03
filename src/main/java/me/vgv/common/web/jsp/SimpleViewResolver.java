package me.vgv.common.web.jsp;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class SimpleViewResolver implements ViewResolver {

	private final String prefix;
	private final String suffix;

	public SimpleViewResolver(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String getJspPath(String viewName) {
		StringBuilder stringBuilder = new StringBuilder(250);

		if (prefix != null) {
			stringBuilder.append(prefix);
		}

		stringBuilder.append(viewName);

		if (suffix != null) {
			stringBuilder.append(suffix);
		}		

		return stringBuilder.toString();
	}
}
