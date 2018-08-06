/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network.packets.out;

import de.tammo.cloud.network.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class WrapperKeyOutPacket implements Packet {

	private String key;

	public WrapperKeyOutPacket(final String key) {
		this.key = key;
	}

	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeUTF(this.key);
	}
}
