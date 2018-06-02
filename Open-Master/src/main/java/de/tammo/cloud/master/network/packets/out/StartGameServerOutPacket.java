/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.packets.out;

import de.tammo.cloud.network.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class StartGameServerOutPacket implements Packet {

	private final String serverGroup;

	private final int port;

	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeUTF(this.serverGroup);
		byteBuf.writeInt(this.port);
	}
}
