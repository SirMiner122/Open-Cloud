/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.packets.out.WrapperKeyValidationOutPacket;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.network.packet.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public class WrapperKeyInPacket implements Packet {

	private String key;

	public final Packet handle(final Channel channel) {
		final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(ServiceProvider.getService(NetworkProviderService.class).getHostFromChannel(channel));
		if (wrapper != null) {
			if (wrapper.getWrapperMeta().getKey().equals(this.key)) {
				wrapper.setVerified(true);
				return new WrapperKeyValidationOutPacket(true);
			}
		}
		return new WrapperKeyValidationOutPacket(false);
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.key = byteBuf.readUTF();
	}
}
