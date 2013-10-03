package me.vgv.common.web.cachemanager;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import me.vgv.common.utils.CloseUtils;
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

	@Inject
	public ResourceManager(ResourceCacheConfiguration cacheConfiguration, ResourceProvider resourceProvider, CacheManager cacheManager) {
		Preconditions.checkNotNull(cacheConfiguration, "cacheConfiguration is null");
		Preconditions.checkNotNull(resourceProvider, "resourceProvider is null");
		Preconditions.checkNotNull(cacheManager, "cacheManager is null");

		this.resourceProvider = resourceProvider;
		this.cache = cacheManager.getEhcache(cacheConfiguration.getCacheName());

		Preconditions.checkNotNull(cache, "cache is null");
	}

	public ResourceEntry getResource(ResourceKey resourceKey) {
		Element element = cache.get(resourceKey);
		if (element != null) {
			return (ResourceEntry) element.getObjectValue();
		}

		InputStream inputStream = resourceProvider.getResource(resourceKey.getResourceName());
		if (inputStream == null) {
			return null;
		}

		try {
			byte[] buffer;

			if (resourceKey.isGzipped()) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStream outputStream = new GZIPOutputStream(byteArrayOutputStream);
				ByteStreams.copy(inputStream, outputStream);
				outputStream.close();
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
			// собственно, закэшируем
			cache.put(new Element(resourceKey, resourceEntry));

			return resourceEntry;
		} catch (IOException e) {
			log.error("can't read static file from ServletContext", e);
			return null;
		} finally {
			CloseUtils.close(inputStream);
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
