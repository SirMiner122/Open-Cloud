/*
 * Copyright (c) 2018. File created by Tammo
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
