/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.network.handler;

import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.packets.in.WrapperKeyInPacket;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.network.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	public void channelActive(final ChannelHandlerContext ctx) {
		final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(ServiceProvider.getService(NetworkProviderService.class).getHostFromChannel(ctx.channel()));
		if (wrapper != null) {
			while (!wrapper.getQueue().isEmpty()) {
				wrapper.sendPacket(wrapper.getQueue().poll());
			}
		} else {
			Logger.warn("Unknown wrapper!");
		}
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(ServiceProvider.getService(NetworkProviderService.class).getHostFromChannel(ctx.channel()));
		if (wrapper != null) {
			if (wrapper.isVerified() || packet instanceof WrapperKeyInPacket) {
				final Packet response = packet.handle(ctx.channel());
				if (response != null) ctx.channel().writeAndFlush(response);
			}
		}
	}

	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(ServiceProvider.getService(NetworkProviderService.class).getHostFromChannel(ctx.channel()));
		wrapper.disconnect();
		Logger.info("Wrapper at host " + wrapper.getWrapperMeta().getHost() + " disconnected!");
		super.channelInactive(ctx);
	}

}
