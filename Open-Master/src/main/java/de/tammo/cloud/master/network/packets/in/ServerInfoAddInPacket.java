/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.network.packets.in;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.components.ComponentsProviderService;
import de.tammo.cloud.master.components.ServerInfo;
import de.tammo.cloud.master.servergroup.ServerGroupService;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

public class ServerInfoAddInPacket implements Packet {

	private String serverGroup;

	private UUID uuid;

	private int port;

	public final Packet handle(final Channel channel) {
		final ServerInfo serverInfo = ServerInfo.builder().group(ServiceProvider.getService(ServerGroupService.class).getServerGroupByName(this.serverGroup)).uuid(this.uuid).port(this.port).build();
		ServiceProvider.getService(ComponentsProviderService.class).addServerInfo(serverInfo);
		return new SuccessPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.serverGroup = byteBuf.readUTF();
		this.uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
		this.port = byteBuf.readInt();
	}

}