/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.network.packets.out;

import de.tammo.cloud.network.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public class WrapperKeyValidationOutPacket implements Packet {

	private boolean valid;

	public WrapperKeyValidationOutPacket(final boolean valid) {
		this.valid = valid;
	}

	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeBoolean(this.valid);
	}
}
