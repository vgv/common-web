package me.vgv.common.web.dispatcher;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class RecursiveInterceptorChainImplTest {

	@Test
	public void testCallNext() throws Exception {
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final AtomicBoolean isHandlerCalled = new AtomicBoolean(false);

		Interceptor first = new Interceptor() {
			@Override
			public void intercept(HttpServletRequest request, HttpServletResponse response, InterceptorChain interceptorChain) throws Exception {
				interceptorChain.callNext(new FirstHttpServletRequestWrapper(request), new FirstHttpServletResponseWrapper(response));
			}
		};

		Interceptor second = new Interceptor() {
			@Override
			public void intercept(HttpServletRequest request, HttpServletResponse response, InterceptorChain interceptorChain) throws Exception {
				interceptorChain.callNext(new SecondHttpServletRequestWrapper(request), new SecondHttpServletResponseWrapper(response));
			}
		};

		Handler handler = new Handler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
				isHandlerCalled.set(true);

				Assert.assertTrue(request instanceof HttpServletRequestWrapper);
				Assert.assertTrue(response instanceof HttpServletResponseWrapper);

				Assert.assertTrue(request instanceof SecondHttpServletRequestWrapper);
				Assert.assertTrue(response instanceof SecondHttpServletResponseWrapper);

				HttpServletRequest parentRequest = (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
				HttpServletResponse parentResponse = (HttpServletResponse) ((HttpServletResponseWrapper) response).getResponse();

				Assert.assertTrue(parentRequest instanceof FirstHttpServletRequestWrapper);
				Assert.assertTrue(parentResponse instanceof FirstHttpServletResponseWrapper);
			}
		};

		RecursiveInterceptorChainImpl chain = new RecursiveInterceptorChainImpl(Arrays.asList(first, second), handler);
		chain.callNext(request, response);
		Assert.assertTrue(isHandlerCalled.get());
	}

	static class FirstHttpServletRequestWrapper extends HttpServletRequestWrapper {
		public FirstHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}
	}

	static class FirstHttpServletResponseWrapper extends HttpServletResponseWrapper {
		public FirstHttpServletResponseWrapper(HttpServletResponse response) {
			super(response);
		}
	}

	static class SecondHttpServletRequestWrapper extends HttpServletRequestWrapper {
		public SecondHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}
	}

	static class SecondHttpServletResponseWrapper extends HttpServletResponseWrapper {
		public SecondHttpServletResponseWrapper(HttpServletResponse response) {
			super(response);
		}
	}
}
