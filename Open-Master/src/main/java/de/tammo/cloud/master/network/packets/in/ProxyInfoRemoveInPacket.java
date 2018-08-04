/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.components.ComponentsProviderService;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

public class ProxyInfoRemoveInPacket implements Packet {

	private UUID uuid;

	public final Packet handle(final Channel channel) {
		ServiceProvider.getService(ComponentsProviderService.class).removeProxyInfo(ServiceProvider.getService(ComponentsProviderService.class).getProxyInfoByUniqueId(this.uuid));
		return new SuccessPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
	}
}
