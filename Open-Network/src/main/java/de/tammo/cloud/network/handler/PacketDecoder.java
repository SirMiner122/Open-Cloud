/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network.handler;

import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.registry.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

	protected void decode(final ChannelHandlerContext ctx, final ByteBuf byteBuf, final List<Object> output) throws Exception {
		final int id = byteBuf.readInt();
		final Packet packet = PacketRegistry.getPacketById(id, PacketRegistry.PacketDirection.IN);

		if (packet == null) {
			new NullPointerException("Could not find packet with id " + id + "!").printStackTrace();
		} else {
			packet.read(new ByteBufInputStream(byteBuf));
			output.add(packet);
		}
	}
}
