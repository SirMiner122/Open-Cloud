/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public class WrapperWorkloadInPacket implements Packet {

	private int cpu;

	private int memory;

	public final Packet handle(final Channel channel) {
		final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByChannel(channel);
		if (wrapper != null) {
			wrapper.setCpu(this.cpu);
			wrapper.setMemory(this.memory);
			return new SuccessPacket();
		}
		return new ErrorPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.cpu = byteBuf.readInt();
		this.memory = byteBuf.readInt();
	}
}
