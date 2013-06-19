package functional.services;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import me.vgv.common.web.dispatcher.DispatchCacheConfiguration;
import me.vgv.common.web.dispatcher.DispatchConfiguration;
import me.vgv.common.web.dispatcher.DispatchEndpoint;
import me.vgv.common.web.dispatcher.DispatchHandler;
import me.vgv.common.web.dispatcher.http.HttpServletRequestThreadLocalContext;
import me.vgv.common.web.dispatcher.http.HttpServletResponseThreadLocalContext;
import me.vgv.common.web.dispatcher.http.Request;
import me.vgv.common.web.dispatcher.http.RequestThreadLocalContext;
import me.vgv.common.web.dispatcher.pattern.Pattern;
import me.vgv.common.web.dispatcher.pattern.Patterns;
import me.vgv.common.web.inject.AbstractGuiceServletContextListener;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;

import javax.management.MBeanServer;
import javax.servlet.ServletContext;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class TestGuiceServletContextListener extends AbstractGuiceServletContextListener {

	@Override
	public List<Module> getModules(ServletContext servletContext) {
		// global interceptor
		final Pattern<Request> globalRequestPattern = Patterns.matchRequest(Patterns.matchPrefix("/"));
		final DispatchEndpoint globalInterceptorEndpoint = new DispatchEndpoint(InterceptorGlobal.class, globalRequestPattern);

		// request 0
		final List<Pattern<Request>> request0Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request0")),
				Patterns.matchRequest(Patterns.matchExact("/request10")),
				Patterns.matchRequest(Patterns.matchExact("/request20")),
				Patterns.matchRequest(Patterns.matchExact("/request30")),
				Patterns.matchRequest(Patterns.matchExact("/request40")),
				Patterns.matchRequest(Patterns.matchExact("/request50")),
				Patterns.matchRequest(Patterns.matchExact("/request60")),
				Patterns.matchRequest(Patterns.matchExact("/request70")),
				Patterns.matchRequest(Patterns.matchExact("/request80")),
				Patterns.matchRequest(Patterns.matchExact("/request90")));
		final DispatchEndpoint handler0Endpoint = new DispatchEndpoint(Handler0.class, request0Patterns);

		// request 1
		final List<Pattern<Request>> request1Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request1")),
				Patterns.matchRequest(Patterns.matchExact("/request11")),
				Patterns.matchRequest(Patterns.matchExact("/request21")),
				Patterns.matchRequest(Patterns.matchExact("/request31")),
				Patterns.matchRequest(Patterns.matchExact("/request41")),
				Patterns.matchRequest(Patterns.matchExact("/request51")),
				Patterns.matchRequest(Patterns.matchExact("/request61")),
				Patterns.matchRequest(Patterns.matchExact("/request71")),
				Patterns.matchRequest(Patterns.matchExact("/request81")),
				Patterns.matchRequest(Patterns.matchExact("/request91")));
		final DispatchEndpoint interceptor11Endpoint = new DispatchEndpoint(Interceptor11.class, request1Patterns);
		final DispatchEndpoint interceptor12Endpoint = new DispatchEndpoint(Interceptor12.class, request1Patterns);
		final DispatchEndpoint interceptor13Endpoint = new DispatchEndpoint(Interceptor13.class, request1Patterns);
		final DispatchEndpoint interceptor14Endpoint = new DispatchEndpoint(Interceptor14.class, request1Patterns);
		final DispatchEndpoint handler1Endpoint = new DispatchEndpoint(Handler1.class, request1Patterns);

		// request 2
		final List<Pattern<Request>> request2Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request2")),
				Patterns.matchRequest(Patterns.matchExact("/request12")),
				Patterns.matchRequest(Patterns.matchExact("/request22")),
				Patterns.matchRequest(Patterns.matchExact("/request32")),
				Patterns.matchRequest(Patterns.matchExact("/request42")),
				Patterns.matchRequest(Patterns.matchExact("/request52")),
				Patterns.matchRequest(Patterns.matchExact("/request62")),
				Patterns.matchRequest(Patterns.matchExact("/request72")),
				Patterns.matchRequest(Patterns.matchExact("/request82")),
				Patterns.matchRequest(Patterns.matchExact("/request92"))
		);
		final DispatchEndpoint handler2Endpoint = new DispatchEndpoint(Handler2.class, request2Patterns);

		// request 3
		final List<Pattern<Request>> request3Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request3")),
				Patterns.matchRequest(Patterns.matchExact("/request13")),
				Patterns.matchRequest(Patterns.matchExact("/request23")),
				Patterns.matchRequest(Patterns.matchExact("/request33")),
				Patterns.matchRequest(Patterns.matchExact("/request43")),
				Patterns.matchRequest(Patterns.matchExact("/request53")),
				Patterns.matchRequest(Patterns.matchExact("/request63")),
				Patterns.matchRequest(Patterns.matchExact("/request73")),
				Patterns.matchRequest(Patterns.matchExact("/request83")),
				Patterns.matchRequest(Patterns.matchExact("/request93"))
		);
		final DispatchEndpoint handler3Endpoint = new DispatchEndpoint(Handler3.class, request3Patterns);

		// request 4
		final List<Pattern<Request>> request4Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request4")),
				Patterns.matchRequest(Patterns.matchExact("/request14")),
				Patterns.matchRequest(Patterns.matchExact("/request24")),
				Patterns.matchRequest(Patterns.matchExact("/request34")),
				Patterns.matchRequest(Patterns.matchExact("/request44")),
				Patterns.matchRequest(Patterns.matchExact("/request54")),
				Patterns.matchRequest(Patterns.matchExact("/request64")),
				Patterns.matchRequest(Patterns.matchExact("/request74")),
				Patterns.matchRequest(Patterns.matchExact("/request84")),
				Patterns.matchRequest(Patterns.matchExact("/request94"))
		);
		final DispatchEndpoint handler4Endpoint = new DispatchEndpoint(Handler4.class, request4Patterns);

		// request 5
		final List<Pattern<Request>> request5Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request5")),
				Patterns.matchRequest(Patterns.matchExact("/request15")),
				Patterns.matchRequest(Patterns.matchExact("/request25")),
				Patterns.matchRequest(Patterns.matchExact("/request35")),
				Patterns.matchRequest(Patterns.matchExact("/request45")),
				Patterns.matchRequest(Patterns.matchExact("/request55")),
				Patterns.matchRequest(Patterns.matchExact("/request65")),
				Patterns.matchRequest(Patterns.matchExact("/request75")),
				Patterns.matchRequest(Patterns.matchExact("/request85")),
				Patterns.matchRequest(Patterns.matchExact("/request95"))
		);
		final DispatchEndpoint interceptor51Endpoint = new DispatchEndpoint(Interceptor51.class, request5Patterns);
		final DispatchEndpoint handler5Endpoint = new DispatchEndpoint(Handler5.class, request5Patterns);

		// request 6
		final List<Pattern<Request>> request6Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request6")),
				Patterns.matchRequest(Patterns.matchExact("/request16")),
				Patterns.matchRequest(Patterns.matchExact("/request26")),
				Patterns.matchRequest(Patterns.matchExact("/request36")),
				Patterns.matchRequest(Patterns.matchExact("/request46")),
				Patterns.matchRequest(Patterns.matchExact("/request56")),
				Patterns.matchRequest(Patterns.matchExact("/request66")),
				Patterns.matchRequest(Patterns.matchExact("/request76")),
				Patterns.matchRequest(Patterns.matchExact("/request86")),
				Patterns.matchRequest(Patterns.matchExact("/request96"))
		);
		final DispatchEndpoint handler6Endpoint = new DispatchEndpoint(Handler6.class, request6Patterns);

		// request 7
		final List<Pattern<Request>> request7Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request7")),
				Patterns.matchRequest(Patterns.matchExact("/request17")),
				Patterns.matchRequest(Patterns.matchExact("/request27")),
				Patterns.matchRequest(Patterns.matchExact("/request37")),
				Patterns.matchRequest(Patterns.matchExact("/request47")),
				Patterns.matchRequest(Patterns.matchExact("/request57")),
				Patterns.matchRequest(Patterns.matchExact("/request67")),
				Patterns.matchRequest(Patterns.matchExact("/request77")),
				Patterns.matchRequest(Patterns.matchExact("/request87")),
				Patterns.matchRequest(Patterns.matchExact("/request97"))
		);
		final DispatchEndpoint interceptor71Endpoint = new DispatchEndpoint(Interceptor71.class, request7Patterns);
		final DispatchEndpoint interceptor72Endpoint = new DispatchEndpoint(Interceptor72.class, request7Patterns);
		final DispatchEndpoint handler7Endpoint = new DispatchEndpoint(Handler7.class, request7Patterns);

		// request 8
		final List<Pattern<Request>> request8Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request8")),
				Patterns.matchRequest(Patterns.matchExact("/request18")),
				Patterns.matchRequest(Patterns.matchExact("/request28")),
				Patterns.matchRequest(Patterns.matchExact("/request38")),
				Patterns.matchRequest(Patterns.matchExact("/request48")),
				Patterns.matchRequest(Patterns.matchExact("/request58")),
				Patterns.matchRequest(Patterns.matchExact("/request68")),
				Patterns.matchRequest(Patterns.matchExact("/request78")),
				Patterns.matchRequest(Patterns.matchExact("/request88")),
				Patterns.matchRequest(Patterns.matchExact("/request98"))
		);
		final DispatchEndpoint handler8Endpoint = new DispatchEndpoint(Handler8.class, request8Patterns);

		// request 9
		final List<Pattern<Request>> request9Patterns = ImmutableList.of(
				Patterns.matchRequest(Patterns.matchExact("/request9")),
				Patterns.matchRequest(Patterns.matchExact("/request19")),
				Patterns.matchRequest(Patterns.matchExact("/request29")),
				Patterns.matchRequest(Patterns.matchExact("/request39")),
				Patterns.matchRequest(Patterns.matchExact("/request49")),
				Patterns.matchRequest(Patterns.matchExact("/request59")),
				Patterns.matchRequest(Patterns.matchExact("/request69")),
				Patterns.matchRequest(Patterns.matchExact("/request79")),
				Patterns.matchRequest(Patterns.matchExact("/request89")),
				Patterns.matchRequest(Patterns.matchExact("/request99"))
		);
		final DispatchEndpoint handler9Endpoint = new DispatchEndpoint(Handler9.class, request9Patterns);

		Module module = new AbstractModule() {
			@Override
			protected void configure() {
				DispatchConfiguration configuration = new DispatchConfiguration(Arrays.asList(
						globalInterceptorEndpoint,
						interceptor11Endpoint,
						interceptor12Endpoint,
						interceptor13Endpoint,
						interceptor14Endpoint,
						interceptor51Endpoint,
						interceptor71Endpoint,
						interceptor72Endpoint,
						handler0Endpoint,
						handler1Endpoint,
						handler2Endpoint,
						handler3Endpoint,
						handler4Endpoint,
						handler5Endpoint,
						handler6Endpoint,
						handler7Endpoint,
						handler8Endpoint,
						handler9Endpoint)
				);
				bind(DispatchConfiguration.class).toInstance(configuration);

				// dispatch handler
				bind(RequestThreadLocalContext.class).toInstance(new RequestThreadLocalContext());
				bind(HttpServletRequestThreadLocalContext.class).toInstance(new HttpServletRequestThreadLocalContext());
				bind(HttpServletResponseThreadLocalContext.class).toInstance(new HttpServletResponseThreadLocalContext());

				bind(DispatchCacheConfiguration.class).toInstance(new DispatchCacheConfiguration("dispatch_cache"));

				CacheManager cacheManager = new CacheManager();
				bind(CacheManager.class).toInstance(cacheManager);
				MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
				ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, true, true);


				bind(DispatchHandler.class).in(Singleton.class);
			}
		};

		return Arrays.asList(module);
	}

}
