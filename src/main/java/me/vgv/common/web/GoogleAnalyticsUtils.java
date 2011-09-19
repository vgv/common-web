package me.vgv.common.web;

import org.apache.commons.lang.StringUtils;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class GoogleAnalyticsUtils {

	public static String getPageTrackingCode(String gaAccount) {
		StringBuilder javascript = new StringBuilder(500);

		javascript.append("<script type=\"text/javascript\">");
		javascript.append("	var _gaq = _gaq || [];");
		javascript.append("	_gaq.push(['_setAccount', '").append(gaAccount).append("']);");
		javascript.append("	_gaq.push(['_trackPageview']);");
		javascript.append("(function() {");
		javascript.append("	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;");
		javascript.append("	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';");
		javascript.append("	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);");
		javascript.append("})();");
		javascript.append("</script>");

		return javascript.toString();
	}

	public static String getEventTrackingCode(String category, String action, String label, String value) {
		StringBuilder javascript = new StringBuilder(200);

		javascript.append("_gaq.push(['_trackEvent', '").append(category).append("', '").append(action).append("'");
		if (!StringUtils.isBlank(label)) {
			javascript.append(", '").append(label).append("'");
			if (!StringUtils.isBlank(value)) {
				javascript.append(", '").append(value).append("'");
			}
		}
		javascript.append("]);");

		return javascript.toString();
	}

	public static String getEventTrackingCode(String category, String action, String label) {
		return getEventTrackingCode(category, action, label, null);
	}

	public static String getEventTrackingCode(String category, String action) {
		return getEventTrackingCode(category, action, null, null);
	}
}
