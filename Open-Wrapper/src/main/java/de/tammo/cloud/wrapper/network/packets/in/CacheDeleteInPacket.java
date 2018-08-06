/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network.packets.in;

import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.service.ServiceProvider;
import de.tammo.cloud.wrapper.components.ServerComponentProviderService;
import io.netty.channel.Channel;

import java.io.IOException;

public class CacheDeleteInPacket implements Packet {

	public final Packet handle(final Channel channel) {
		try {
			FileUtils.deleteDir(ServiceProvider.getService(ServerComponentProviderService.class).getCache());
			return new SuccessPacket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ErrorPacket();
	}

}
