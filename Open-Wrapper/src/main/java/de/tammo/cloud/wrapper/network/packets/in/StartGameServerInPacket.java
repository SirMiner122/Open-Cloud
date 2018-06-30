/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.network.packets.in;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.wrapper.components.ServerComponentProviderService;
import de.tammo.cloud.wrapper.components.server.GameServer;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

public class StartGameServerInPacket implements Packet {

	private String serverGroup;

	private int port;

	public final Packet handle(final Channel channel) {
		if (this.serverGroup == null || this.port == 0) {
			return new ErrorPacket();
		}

		final GameServer gameServer = new GameServer(this.serverGroup, UUID.randomUUID(), this.port);

		ServiceProvider.getService(ServerComponentProviderService.class).offerServerComponent(gameServer);

		return new SuccessPacket();
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException{
		this.serverGroup = byteBuf.readUTF();
		this.port = byteBuf.readInt();
	}
}
