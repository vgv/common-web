package me.vgv.common.web.dispatcher;


import me.vgv.common.web.dispatcher.http.HttpMethod;
import me.vgv.common.web.dispatcher.http.HttpSchema;
import me.vgv.common.web.dispatcher.http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchUtils {

	private static final Logger log = LoggerFactory.getLogger(DispatchUtils.class);

	public static Request createRequest(HttpServletRequest request) {
		String hostName = request.getServerName();
		HttpMethod httpMethod = HttpMethod.findByString(request.getMethod());
		HttpSchema httpSchema = request.isSecure() ? HttpSchema.HTTPS : HttpSchema.HTTP;
		String uri = extractRequestUri(request);

		return new Request(hostName, httpMethod, httpSchema, uri);
	}

	public static String extractRequestUri(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public static DispatchChain createDispatchChain(DispatchConfiguration dispatchConfiguration, Request request) {
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		List<Handler> handlers = new ArrayList<Handler>();

		for (DispatchEndpoint dispatchEndpoint : dispatchConfiguration.getDispatchEndpoints()) {
			if (dispatchEndpoint.match(request)) {
				Object requestProcessor = dispatchEndpoint.getEndpoint();
				if (requestProcessor instanceof Interceptor) {
					interceptors.add((Interceptor) requestProcessor);
				} else {
					handlers.add((Handler) requestProcessor);
				}
			}
		}

		if (interceptors.isEmpty() && handlers.isEmpty()) {
			// никаких обработчиков этого запроса вообще не найдено
			return null;
		} else {
			// все-таки что-то найдено
			Handler handler = handlers.isEmpty() ? null : handlers.get(0);
			return new DispatchChain(request, interceptors, handler);
		}
	}

}
