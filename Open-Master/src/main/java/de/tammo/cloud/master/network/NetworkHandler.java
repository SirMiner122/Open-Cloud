/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network;

import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.master.network.wrapper.WrapperMeta;
import io.netty.channel.Channel;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.*;

public class NetworkHandler {

	@Getter
	private final ArrayList<Wrapper> wrappers = new ArrayList<>();

	public final Wrapper getWrapperByHost(final String host) {
		return this.wrappers.stream().filter(wrapper -> wrapper.getWrapperMeta().getHost().equalsIgnoreCase(host)).findFirst().orElse(null);
	}

	public void addWrapper(final Wrapper wrapper) {
		this.wrappers.add(wrapper);
	}

	public void removeWrapper(final Wrapper wrapper) {
		this.wrappers.remove(wrapper);
	}

	public final Wrapper getWrapperByChannel(final Channel channel) {
		return this.wrappers.stream().filter(wrapper -> wrapper.getWrapperMeta().getHost().equalsIgnoreCase(this.getHostFromChannel(channel))).findFirst().orElse(null);
	}

	public final String getHostFromChannel(final Channel channel) {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
	}

	public final void createWrappers(final ArrayList<WrapperMeta> wrapperMetas) {
		wrapperMetas.forEach(wrapperMeta -> this.addWrapper(new Wrapper(wrapperMeta)));
	}

	public final ArrayList<WrapperMeta> getWrapperMetas() {
		final ArrayList<WrapperMeta> metas = new ArrayList<>();
		this.wrappers.forEach(wrapper -> metas.add(wrapper.getWrapperMeta()));
		return metas;
	}

	public boolean isWhitelisted(final String ip) {
		return this.wrappers.stream().filter(wrapper -> wrapper.getWrapperMeta().getHost().equalsIgnoreCase(ip)).findFirst().orElse(null) != null;
	}

	public final String generateWrapperKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private Wrapper getBestWrapper() {
		// cpu -> ram
		final Wrapper bestCpu = this.wrappers.stream().min(Comparator.comparing(Wrapper::getCpu)).orElse(null);
		final Wrapper bestMemory = this.wrappers.stream().min(Comparator.comparing(Wrapper::getMemory)).orElse(null);

		if (bestCpu == null || bestMemory == null) {
			return null;
		}

		if (bestCpu == bestMemory) {
			return bestCpu;
		} else {

		}

		return null;
	}

}
