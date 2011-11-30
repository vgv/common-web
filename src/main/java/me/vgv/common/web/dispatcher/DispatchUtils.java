package me.vgv.common.web.dispatcher;


import com.google.inject.Injector;
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

	public static DispatchChain createDispatchChain(DispatchConfiguration dispatchConfiguration, Request request, Injector injector) {
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		List<Handler> handlers = new ArrayList<Handler>();

		for (DispatchEndpoint dispatchEndpoint : dispatchConfiguration.getDispatchEndpoints()) {
			if (dispatchEndpoint.match(request)) {
				Class<? extends Service> requestProcessor = dispatchEndpoint.getEndpoint();
				Service service = injector.getInstance(requestProcessor);
				if (service instanceof Interceptor) {
					interceptors.add((Interceptor) service);
				} else if (service instanceof Handler) {
					handlers.add((Handler) service);
				} else {
					throw new IllegalArgumentException("Service class is " + service.getClass() + ". Do you forget to process such type of service?");
				}
			}
		}

		if (handlers.isEmpty()) {
			// никаких обработчиков этого запроса вообще не найдено
			return null;
		} else {
			// все-таки какой-то обработчик найден, возможно вместе с интерцепторами
			Handler handler = handlers.get(0);
			return new DispatchChain(interceptors, handler);
		}
	}

}
