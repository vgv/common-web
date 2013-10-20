package me.vgv.common.web.cachemanager.provider;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public class FileSystemResourceProviderTest {

	@Test(groups = "unit")
	public void testGetResource_WithoutPrefix() throws Exception {
		File file = File.createTempFile("commons-web", null);

		ResourceProvider resourceProvider = new FileSystemResourceProvider();
		Assert.assertNotNull(resourceProvider.getResource(file.getAbsolutePath()));
	}
}
