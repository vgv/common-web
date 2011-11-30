package functional;

import com.google.common.io.ByteStreams;
import functional.services.TestGuiceServletContextListener;
import me.vgv.common.web.dispatcher.DispatchServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class FunctionalTest {

	public static final int SERVER_PORT = 43875;

	private Server server;

	@BeforeClass(groups = "functional")
	public void init() throws Exception {
		server = new Server(SERVER_PORT);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addEventListener(new TestGuiceServletContextListener());
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(DispatchServlet.class), "/");

		server.start();
	}

	@AfterClass(groups = "functional")
	public void destroy() throws Exception {
		server.stop();
	}

	@Test(groups = "functional")
	public void testRequest1() throws Exception {
		// тестируем запрос с глобальным и несколькими конкретными интерцепторами по дороге
		String url = "http://localhost:" + SERVER_PORT + "/request1";
		URLConnection urlConnection = new URL(url).openConnection();
		InputStream inputStream = urlConnection.getInputStream();
		String content = new String(ByteStreams.toByteArray(inputStream), "UTF-8");
		inputStream.close();

		Assert.assertEquals("interceptor_global" + "interceptor11" + "interceptor12" + "interceptor13" + "interceptor14" + "handler1", content);
	}

	@Test(groups = "functional")
	public void testRequest2() throws Exception {
		// тестируем запрос только с глобальным интерцептором и одним handler'ом
		String url = "http://localhost:" + SERVER_PORT + "/request2";
		URLConnection urlConnection = new URL(url).openConnection();
		InputStream inputStream = urlConnection.getInputStream();
		String content = new String(ByteStreams.toByteArray(inputStream), "UTF-8");
		inputStream.close();

		Assert.assertEquals("interceptor_global" + "handler2", content);
	}

	@Test(groups = "functional")
	public void testRequestNotFound() throws Exception {
		// тестируем сервис с неопределенным endpoint'ом
		String url = "http://localhost:" + SERVER_PORT + "/request_not_found";

		URLConnection urlConnection = new URL(url).openConnection();
		Assert.assertEquals(404, ((HttpURLConnection) urlConnection).getResponseCode());
		Assert.assertEquals("Page not found", ((HttpURLConnection) urlConnection).getResponseMessage());
	}

	@Test(groups = {"functional", "load"}, timeOut = 10000)
	public void testPerformance() throws Exception {
		// тестируем производительность Dispatch-системы в реальном режиме
		final Random rnd = new Random();
		final String[] responses = new String[]{
				"interceptor_global" + "handler0",
				"interceptor_global" + "interceptor11" + "interceptor12" + "interceptor13" + "interceptor14" + "handler1",
				"interceptor_global" + "handler2",
				"interceptor_global" + "handler3",
				"interceptor_global" + "handler4",
				"interceptor_global" + "interceptor51" + "handler5",
				"interceptor_global" + "handler6",
				"interceptor_global" + "interceptor71" + "interceptor72" + "handler7",
				"interceptor_global" + "handler8",
				"interceptor_global" + "handler9"
		};

		final String[] urls = new String[100];
		for (int i = 0; i < 100; i++) {
			urls[i] = "http://localhost:" + SERVER_PORT + "/request" + i;
		}

		for (int i = 0; i < 10000; i++) {
			final int REQUEST_ID = rnd.nextInt(100); // число от 1 до 9
			final String url = urls[REQUEST_ID];
			final String response = responses[REQUEST_ID % 10];

			URLConnection urlConnection = new URL(url).openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			String content = new String(ByteStreams.toByteArray(inputStream), "UTF-8");
			inputStream.close();

			Assert.assertEquals(response, content);
		}
	}

}