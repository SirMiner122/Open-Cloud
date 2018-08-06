/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network.packet.impl;

import de.tammo.cloud.network.packet.Packet;
import io.netty.channel.Channel;

public class SuccessPacket implements Packet {

	public final Packet handle(final Channel channel) {
		return null;
	}

}
