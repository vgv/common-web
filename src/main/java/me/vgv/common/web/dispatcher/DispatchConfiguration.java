package me.vgv.common.web.dispatcher;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public final class DispatchConfiguration {

	private final List<DispatchEndpoint> dispatchEndpoints;

	public DispatchConfiguration(List<DispatchEndpoint> dispatchEndpoints) {
		this.dispatchEndpoints = ImmutableList.copyOf(dispatchEndpoints);
	}

	public List<DispatchEndpoint> getDispatchEndpoints() {
		return dispatchEndpoints;
	}

	public static class Builder {

		private List<DispatchEndpoint> dispatchEndpoints = new ArrayList<DispatchEndpoint>();

		public Builder addDispatchEndpoint(DispatchEndpoint dispatchEndpoint) {
			this.dispatchEndpoints.add(dispatchEndpoint);
			return this;
		}

		public DispatchConfiguration build() {
			return new DispatchConfiguration(this.dispatchEndpoints);
		}
	}
}
