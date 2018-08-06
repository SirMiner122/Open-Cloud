/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
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
	private transient Channel channel;

	@Setter
	private boolean verified = false;

	@Setter
	private int cpu = 0;

	@Setter
	private int memory = 0;

	private transient final ConcurrentLinkedQueue<Packet> queue = new ConcurrentLinkedQueue<>();

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
