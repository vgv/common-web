package me.vgv.common.web.cachemanager.provider;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class RetryResourceProvider implements ResourceProvider {

	private static final Logger log = LoggerFactory.getLogger(RetryResourceProvider.class);

	private final ResourceProvider peer;
	private final int maxRetryCount;
	private final int everyRetryPause;

	public RetryResourceProvider(ResourceProvider peer, int maxRetryCount, int everyRetryPause) {
		Preconditions.checkNotNull(peer, "peer is null");
		Preconditions.checkArgument(maxRetryCount > 0, "maxRetryCount is " + maxRetryCount);
		Preconditions.checkArgument(everyRetryPause > 0, "everyRetryPause is " + everyRetryPause);

		this.peer = peer;
		this.maxRetryCount = maxRetryCount;
		this.everyRetryPause = everyRetryPause;
	}

	public RetryResourceProvider(ResourceProvider peer) {
		this(peer, 600, 100); // 1 min by default
	}

	@Override
	public InputStream getResource(String resourceName) throws IOException {
		InputStream inputStream = peer.getResource(resourceName);

		int attempts = maxRetryCount;
		while (inputStream == null && attempts-- > 0) {
			// wait
			try {
				Thread.sleep(everyRetryPause);
			} catch (InterruptedException e) {
				log.warn("Sleep interrupted", e);
				throw new IOException(e);
			}

			// try to get stream again
			log.debug("Try to get resource again, attempts remaining - " + attempts);
			inputStream = peer.getResource(resourceName);
		}

		return inputStream;
	}
}
