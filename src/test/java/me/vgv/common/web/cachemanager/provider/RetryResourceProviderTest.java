package me.vgv.common.web.cachemanager.provider;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class RetryResourceProviderTest {

	@Test(groups = "unit")
	public void testGetResource_IfResourceNotFound() throws Exception {
		ResourceProvider peer = Mockito.mock(ResourceProvider.class);
		RetryResourceProvider retryResourceProvider = new RetryResourceProvider(peer, 5, 500);

		long start = System.currentTimeMillis();
		Assert.assertNull(retryResourceProvider.getResource("/any/resource"));
		long end = System.currentTimeMillis();
		Assert.assertTrue((end - start) > 5 * 500);
	}

	@Test(groups = "unit")
	public void testGetResource() throws Exception {
		final InputStream originalInputStream = Mockito.mock(InputStream.class);
		ResourceProvider resourceProvider = new ResourceProvider() {
			private int count = 5;

			@Override
			public InputStream getResource(String resourceName) throws IOException {
				if (count-- > 0) {
					return null;
				} else {
					return originalInputStream;
				}
			}
		};

		RetryResourceProvider retryResourceProvider = new RetryResourceProvider(resourceProvider, 10, 500);
		long start = System.currentTimeMillis();
		InputStream inputStream = retryResourceProvider.getResource("/some/resource");
		long end = System.currentTimeMillis();

		Assert.assertSame(inputStream, originalInputStream);
		Assert.assertTrue((end - start) > 5 * 500);

	}

}
