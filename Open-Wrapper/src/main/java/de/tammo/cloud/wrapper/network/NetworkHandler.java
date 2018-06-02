/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.network;

import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.wrapper.components.proxy.ProxyServer;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class NetworkHandler {

	@Setter
	private Channel masterChannel;

	@Getter
	private final ConcurrentLinkedQueue<Packet> queue = new ConcurrentLinkedQueue<>();

	@Getter
	private final ExecutorService executorService = Executors.newCachedThreadPool();

	private final ProxyServer proxyServer = new ProxyServer();

	public void startProxy() {
		this.executorService.submit(() -> {
			try {
				this.proxyServer.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void sendPacketToMaster(final Packet packet) {
		if (this.isConnected()) {
			this.masterChannel.writeAndFlush(packet);
		} else {
			this.queue.offer(packet);
		}
	}

	private boolean isConnected() {
		return this.masterChannel != null && this.masterChannel.isActive() && this.masterChannel.isOpen();
	}

	public final String getHostFromChannel(final Channel channel) {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
	}

}
