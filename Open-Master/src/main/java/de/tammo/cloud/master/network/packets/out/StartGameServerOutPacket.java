/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
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
