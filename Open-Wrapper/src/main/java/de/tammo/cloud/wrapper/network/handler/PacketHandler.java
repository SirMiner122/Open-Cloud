/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.network.handler;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.network.NetworkProviderService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	public void channelActive(final ChannelHandlerContext ctx) {
		if (ServiceProvider.getService(NetworkProviderService.class).getHostFromChannel(ctx.channel()).equals("127.0.0.1")) {
			ServiceProvider.getService(NetworkProviderService.class).setMasterChannel(ctx.channel());
			while (!ServiceProvider.getService(NetworkProviderService.class).getQueue().isEmpty()) {
				ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(ServiceProvider.getService(NetworkProviderService.class).getQueue().poll());
			}
		}
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Packet response = packet.handle(ctx.channel());
		if (response != null) ctx.channel().writeAndFlush(response);
	}

	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		Logger.info("The Master declined the connection!");
		if (Wrapper.getWrapper().isRunning()) {
			Wrapper.getWrapper().shutdown();
		}
		super.channelInactive(ctx);
	}
}
