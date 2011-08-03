package me.vgv.common.web.cachemanager;

import com.google.common.io.ByteStreams;
import me.vgv.common.utils.CloseUtils;
import me.vgv.common.utils.hash.HashAlgorithm;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.String;import java.util.zip.GZIPOutputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceManager {

	private static final Logger log = LoggerFactory.getLogger(ResourceManager.class);

	private final ServletContext servletContext;
	private final Ehcache cache;

	public ResourceManager(ServletContext servletContext, Ehcache cache) {
		this.servletContext = servletContext;
		this.cache = cache;
	}

	public ResourceEntry getResource(ResourceKey resourceKey) {
		Element element = cache.get(resourceKey);
		if (element != null) {
			return (ResourceEntry) element.getValue();
		}

		InputStream inputStream = getResourceInputStream(resourceKey.getResourceName());
		if (inputStream == null) {
			return null;
		}

		try {
			byte[] buffer = null;

			if (resourceKey.isGzipped()) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStream outputStream = new GZIPOutputStream(byteArrayOutputStream);
				ByteStreams.copy(inputStream, outputStream);
				outputStream.close();
				buffer = byteArrayOutputStream.toByteArray();
			} else {
				buffer = ByteStreams.toByteArray(inputStream);
			}

			CacheMode cacheMode = defineCacheMode(resourceKey.getResourceName());
			String etag = null;
			if (cacheMode == CacheMode.CACHE_BY_ETAG) {
				etag = HashAlgorithm.MD5.generateHash(buffer);
			}

			// пересоздадим resourceKey, тут мог появиться etag
			resourceKey = new ResourceKey(resourceKey.getResourceName(), resourceKey.isGzipped(), etag);
			// создадим resourceEntry
			ResourceEntry resourceEntry = new ResourceEntry(buffer, etag, cacheMode);
			// собственно, закешируем
			cache.put(new Element(resourceKey, resourceEntry));

			return resourceEntry;
		} catch (IOException e) {
			log.error("can't read static file from ServletContext", e);
			return null;
		} finally {
			CloseUtils.close(inputStream);
		}
	}

	private InputStream getResourceInputStream(String resourceName) {
		// все хорошо, это файл и он читается
		return servletContext.getResourceAsStream(resourceName);
	}

	private CacheMode defineCacheMode(String resourceName) {
		if (resourceName.contains(".cache.")) {
			return CacheMode.CACHE_FOREVER;
		} else if (resourceName.contains(".nocache.")) {
			return CacheMode.CACHE_NEVER;
		} else {
			return CacheMode.CACHE_BY_ETAG;
		}
	}

}
