/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network.packet;

import de.tammo.cloud.network.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public interface Packet {

	default void read(final ByteBufInputStream byteBuf) throws IOException {}

	default void write(final ByteBufOutputStream byteBuf) throws IOException {}

	default Packet handle(final Channel channel) {

		return new SuccessPacket();
	}

}
