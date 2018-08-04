/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network.packets.in;

import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.wrapper.Wrapper;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public class WrapperKeyValidationInPacket implements Packet {

	private boolean valid;

	public final Packet handle(final Channel channel) {
		if (this.valid) {
			Logger.info("Wrapper key is valid!");
			Wrapper.getWrapper().getConfiguration().setKey(Wrapper.getWrapper().getKey());
			Wrapper.getWrapper().setVerified(true);
			return new SuccessPacket();
		} else {
			Logger.info("Wrapper key is not valid!");
			Wrapper.getWrapper().shutdown();
			return null;
		}
	}

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.valid = byteBuf.readBoolean();
	}
}
