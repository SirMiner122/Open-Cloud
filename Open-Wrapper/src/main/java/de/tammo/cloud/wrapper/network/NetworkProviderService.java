/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network;

import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.service.Service;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkProviderService implements Service {

	@Setter
	private Channel masterChannel;

	@Getter
	private final ConcurrentLinkedQueue<Packet> queue = new ConcurrentLinkedQueue<>();

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
