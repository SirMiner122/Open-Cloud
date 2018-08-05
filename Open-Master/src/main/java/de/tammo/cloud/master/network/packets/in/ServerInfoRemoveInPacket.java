/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.master.components.ComponentsProviderService;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.service.ServiceProvider;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

public class ServerInfoRemoveInPacket implements Packet {

	private UUID uuid;

	public final Packet handle(final Channel channel) {
		ServiceProvider.getService(ComponentsProviderService.class).removeServerInfo(ServiceProvider.getService(ComponentsProviderService.class).getServerInfoByUniqueId(this.uuid));
		return new SuccessPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
	}

}
