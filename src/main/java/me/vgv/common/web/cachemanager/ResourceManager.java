package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import me.vgv.common.utils.hash.HashAlgorithm;
import me.vgv.common.web.cachemanager.provider.ResourceProvider;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class ResourceManager {

	private static final Logger log = LoggerFactory.getLogger(ResourceManager.class);

	private static final ThreadLocal<MimetypesFileTypeMap> MIMETYPES_FILE_TYPE_MAP_THREAD_LOCAL = new ThreadLocal<MimetypesFileTypeMap>() {
		@Override
		protected MimetypesFileTypeMap initialValue() {
			return new MimetypesFileTypeMap();
		}
	};

	private final ResourceProvider resourceProvider;
	private final Ehcache cache;

	public ResourceManager(ResourceCacheConfiguration cacheConfiguration, ResourceProvider resourceProvider, CacheManager cacheManager) {
		Preconditions.checkNotNull(cacheConfiguration, "cacheConfiguration is null");
		Preconditions.checkNotNull(resourceProvider, "resourceProvider is null");
		Preconditions.checkNotNull(cacheManager, "cacheManager is null");

		this.resourceProvider = resourceProvider;
		this.cache = cacheManager.getEhcache(cacheConfiguration.getCacheName());

		Preconditions.checkNotNull(cache, "cache is null");
	}

	public ResourceManager(ResourceProvider resourceProvider) {
		Preconditions.checkNotNull(resourceProvider, "resourceProvider is null");

		this.resourceProvider = resourceProvider;
		this.cache = null;
	}

	public ResourceEntry getResource(ResourceKey resourceKey) {
		// посмотрим в кеше
		if (cache != null) {
			Element element = cache.get(resourceKey);
			if (element != null) {
				return (ResourceEntry) element.getObjectValue();
			}
		}

		try (InputStream inputStream = resourceProvider.getResource(resourceKey.getResourceName())) {
			if (inputStream == null) {
				return null;
			}

			byte[] buffer;

			if (resourceKey.isGzipped()) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4 * 1024);
				try (OutputStream outputStream = new GZIPOutputStream(byteArrayOutputStream)) {
					ByteStreams.copy(inputStream, outputStream);
				}
				buffer = byteArrayOutputStream.toByteArray();
			} else {
				buffer = ByteStreams.toByteArray(inputStream);
			}

			ResourceCacheMode cacheMode = defineCacheMode(resourceKey.getResourceName());
			String etag = null;
			if (cacheMode == ResourceCacheMode.CACHE_BY_ETAG) {
				etag = HashAlgorithm.MD5.generateHash(buffer);
			}

			// пересоздадим resourceKey, тут мог появиться etag
			resourceKey = new ResourceKey(resourceKey.getResourceName(), resourceKey.isGzipped(), etag);
			// создадим resourceEntry
			ResourceEntry resourceEntry = new ResourceEntry(buffer, etag, cacheMode, MIMETYPES_FILE_TYPE_MAP_THREAD_LOCAL.get().getContentType(resourceKey.getResourceName()));

			// собственно, закэшируем если кеширование включено
			if (cache != null) {
				cache.put(new Element(resourceKey, resourceEntry));
			}

			return resourceEntry;
		} catch (IOException e) {
			log.error("can't read static file from ServletContext", e);
			return null;
		}
	}

	private ResourceCacheMode defineCacheMode(String resourceName) {
		if (resourceName.contains(".cache.")) {
			return ResourceCacheMode.CACHE_FOREVER;
		} else if (resourceName.contains(".nocache.")) {
			return ResourceCacheMode.CACHE_NEVER;
		} else {
			return ResourceCacheMode.CACHE_BY_ETAG;
		}
	}
}
