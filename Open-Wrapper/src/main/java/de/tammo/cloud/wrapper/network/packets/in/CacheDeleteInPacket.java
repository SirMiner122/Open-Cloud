/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.network.packets.in;

import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.wrapper.Wrapper;
import io.netty.channel.Channel;

import java.io.IOException;
import java.nio.file.Files;

public class CacheDeleteInPacket implements Packet {

	public final Packet handle(final Channel channel) {
		try {
			Files.deleteIfExists(Wrapper.getWrapper().getServerComponentHandler().getCache().toPath());
			return new SuccessPacket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ErrorPacket();
	}

}
