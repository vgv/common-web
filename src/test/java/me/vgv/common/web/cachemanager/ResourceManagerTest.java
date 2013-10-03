package me.vgv.common.web.cachemanager;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public class ResourceManagerTest {

	@Test(groups = "unit")
	public void testGetResourceIfFoundInCache() throws Exception {
		// servlet context
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		//
		ResourceCacheConfiguration cacheConfiguration = new ResourceCacheConfiguration("cache_name");

		//
		ResourceKey resourceKey = new ResourceKey("some_file", true, "asSDF3245fsdq");
		ResourceEntry resourceEntry = new ResourceEntry("sdfsdfsdfsf".getBytes(), "asSDF3245fsdq", ResourceCacheMode.CACHE_BY_ETAG, "mime/type");
		Element element = new Element(resourceKey, resourceEntry);
		Ehcache ehcache = Mockito.mock(Ehcache.class);
		Mockito.when(ehcache.get(resourceKey)).thenReturn(element);

		//
		CacheManager cacheManager = Mockito.mock(CacheManager.class);
		Mockito.when(cacheManager.getEhcache("cache_name")).thenReturn(ehcache);

		//
		ResourceManager resourceManager = new ResourceManager(cacheConfiguration, servletContext, cacheManager);
		Object result = resourceManager.getResource(resourceKey);

		//
		Assert.assertSame(resourceEntry, result);
		Mockito.verify(ehcache).get(resourceKey);
	}

	@Test(groups = "unit")
	public void testGetResourceIfNotFoundInServletContext() throws Exception {
		//
		ResourceCacheConfiguration cacheConfiguration = new ResourceCacheConfiguration("cache_name");

		//
		Ehcache ehcache = Mockito.mock(Ehcache.class);
		Mockito.when(ehcache.get(Mockito.anyObject())).thenReturn(null);

		//
		CacheManager cacheManager = Mockito.mock(CacheManager.class);
		Mockito.when(cacheManager.getEhcache("cache_name")).thenReturn(ehcache);

		// servlet context
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(servletContext.getResourceAsStream(Mockito.anyString())).thenReturn(null);

		//
		ResourceKey resourceKey = new ResourceKey("some_file", true, "asSDF3245fsdq");

		//
		ResourceManager resourceManager = new ResourceManager(cacheConfiguration, servletContext, cacheManager);

		//
		Assert.assertNull(resourceManager.getResource(resourceKey));
		Mockito.verify(ehcache).get(resourceKey);
		Mockito.verify(servletContext).getResourceAsStream(resourceKey.getResourceName());
	}

	@Test(groups = "unit")
	public void testGetResourceIfFoundInServletContext_Plain() throws Exception {
		//
		ResourceCacheConfiguration cacheConfiguration = new ResourceCacheConfiguration("cache_name");

		//
		Ehcache ehcache = Mockito.mock(Ehcache.class);
		Mockito.when(ehcache.get(Mockito.anyObject())).thenReturn(null);

		//
		CacheManager cacheManager = Mockito.mock(CacheManager.class);
		Mockito.when(cacheManager.getEhcache("cache_name")).thenReturn(ehcache);

		//
		ResourceKey resourceKey = new ResourceKey("/style.css", false, "asSDF3245fsdq");

		// servlet context
		String str = "АПвр3563ывдлпрыSEFW^$3akd';ge";
		for (int i = 0; i < 1000; i++) {
			str += "sdlkgher534&slekgfhjфАУГ57одлр4оащзшропыкжезрол";
		}
		byte[] data = str.getBytes("UTF-8");
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(servletContext.getResourceAsStream(resourceKey.getResourceName())).thenReturn(new ByteArrayInputStream(data));

		//
		ResourceManager resourceManager = new ResourceManager(cacheConfiguration, servletContext, cacheManager);

		//
		ResourceEntry resourceEntry = resourceManager.getResource(resourceKey);

		// проверим вызовы
		Mockito.verify(ehcache).get(resourceKey);
		Mockito.verify(servletContext).getResourceAsStream(resourceKey.getResourceName());
		Mockito.verify(ehcache).put(Mockito.isA(Element.class));

		// проверим данные
		Assert.assertEquals(resourceEntry.getData(), data);
		Assert.assertEquals(resourceEntry.getEtag(), "023fb2b39c29d1b99a0c9729dc9205e2");
		Assert.assertEquals(resourceEntry.getCacheMode(), ResourceCacheMode.CACHE_BY_ETAG);
		Assert.assertEquals(resourceEntry.getMimeType(), "text/css");
	}

	@Test(groups = "unit")
	public void testGetResourceIfFoundInServletContext_Gzip() throws Exception {
		//
		ResourceCacheConfiguration cacheConfiguration = new ResourceCacheConfiguration("cache_name");

		//
		Ehcache ehcache = Mockito.mock(Ehcache.class);
		Mockito.when(ehcache.get(Mockito.anyObject())).thenReturn(null);

		//
		CacheManager cacheManager = Mockito.mock(CacheManager.class);
		Mockito.when(cacheManager.getEhcache("cache_name")).thenReturn(ehcache);

		//
		ResourceKey resourceKey = new ResourceKey("/style.css", true, "asSDF3245fsdq");

		// servlet context
		String str = "АПвр3563ывдлпУ:.%гнрдлрпкфшдорпуфшп3akd';ge";
		for (int i = 0; i < 1000; i++) {
			str += "sdlkgher534&slekgfhjxcvkjndilurhtwoejgeowpughукндгшрщ5289нецдклзфпьжфщыуитож57одлр4оащзшропыкжезрол";
		}
		byte[] data = str.getBytes("UTF-8");
		ServletContext servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(servletContext.getResourceAsStream(resourceKey.getResourceName())).thenReturn(new ByteArrayInputStream(data));

		//
		ResourceManager resourceManager = new ResourceManager(cacheConfiguration, servletContext, cacheManager);

		//
		ResourceEntry resourceEntry = resourceManager.getResource(resourceKey);

		// проверим вызовы
		Mockito.verify(ehcache).get(resourceKey);
		Mockito.verify(servletContext).getResourceAsStream(resourceKey.getResourceName());
		Mockito.verify(ehcache).put(Mockito.isA(Element.class));

		// проверим данные

		// сожмем
		ByteArrayOutputStream gzippedData = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(gzippedData);
		gzipOutputStream.write(data);
		gzipOutputStream.close();
		Assert.assertEquals(resourceEntry.getData(), gzippedData.toByteArray());

		Assert.assertEquals(resourceEntry.getEtag(), "1daee29de362f837adc3da044d1914c2");
		Assert.assertEquals(resourceEntry.getCacheMode(), ResourceCacheMode.CACHE_BY_ETAG);
		Assert.assertEquals(resourceEntry.getMimeType(), "text/css");
	}

}
