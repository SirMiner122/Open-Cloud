/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network.packets.out;

import de.tammo.cloud.network.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class ProxyInfoAddOutPacket implements Packet {

	private final UUID uuid;

	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeLong(this.uuid.getMostSignificantBits());
		byteBuf.writeLong(this.uuid.getLeastSignificantBits());
	}

}

