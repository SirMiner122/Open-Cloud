/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.wrapper;

import de.tammo.cloud.network.packet.Packet;
import io.netty.channel.Channel;
import lombok.*;

import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@RequiredArgsConstructor
public class Wrapper {

	private final WrapperMeta wrapperMeta;

	@Setter
	private Channel channel;

	@Setter
	private boolean verified = false;

	@Setter
	private int cpu = 0;

	@Setter
	private int memory = 0;

	private final ConcurrentLinkedQueue<Packet> queue = new ConcurrentLinkedQueue<>();

	public void sendPacket(final Packet packet) {
		if (this.channel == null) {
			this.queue.offer(packet);
		} else {
			this.channel.writeAndFlush(packet);
		}
	}

	public void disconnect() {
		this.channel.close();
		this.channel = null;
		this.queue.clear();
	}

	public final boolean isConnected() {
		return this.channel != null && this.channel.isOpen();
	}

}
