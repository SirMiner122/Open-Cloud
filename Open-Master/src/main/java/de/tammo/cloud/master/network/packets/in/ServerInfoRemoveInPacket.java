/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.master.Master;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

public class ServerInfoRemoveInPacket implements Packet {

	private UUID uuid;

	public final Packet handle(final Channel channel) {
		Master.getMaster().getComponentsHandler().removeServerInfo(Master.getMaster().getComponentsHandler().getServerInfoByUniqueId(this.uuid));
		return new SuccessPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
	}

}
