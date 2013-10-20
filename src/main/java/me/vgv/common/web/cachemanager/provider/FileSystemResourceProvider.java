package me.vgv.common.web.cachemanager.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vasily.vasilkov@gmail.com)
 */
public class FileSystemResourceProvider implements ResourceProvider {

	private static final Logger log = LoggerFactory.getLogger(FileSystemResourceProvider.class);

	private final String prefix;
	private final String suffix;

	public FileSystemResourceProvider() {
		this(null, null);
	}

	public FileSystemResourceProvider(String prefix) {
		this(prefix, null);
	}

	public FileSystemResourceProvider(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public InputStream getResource(String resourceName) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (prefix != null) {
			sb.append(prefix);
		}
		sb.append(resourceName);
		if (suffix != null) {
			sb.append(suffix);
		}

		String path = sb.toString();
		log.debug("Try to get file {}", path);
		return new FileInputStream(path);
	}
}
