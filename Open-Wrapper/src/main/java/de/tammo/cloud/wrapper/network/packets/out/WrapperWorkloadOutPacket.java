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

@RequiredArgsConstructor
public class WrapperWorkloadOutPacket implements Packet {

	private final int cpu;

	private final int memory;

	public void write(final ByteBufOutputStream byteBuf) throws IOException{
		byteBuf.writeInt(this.cpu);
		byteBuf.writeInt(this.memory);
	}
}
