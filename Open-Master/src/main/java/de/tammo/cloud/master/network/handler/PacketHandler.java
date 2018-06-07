/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.network.handler;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.master.network.packets.in.WrapperKeyInPacket;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.network.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	public void channelActive(final ChannelHandlerContext ctx) {
		final Wrapper wrapper = Master.getMaster().getNetworkHandler().getWrapperByHost(Master.getMaster().getNetworkHandler().getHostFromChannel(ctx.channel()));
		if (wrapper != null) {
			while (!wrapper.getQueue().isEmpty()) {
				wrapper.sendPacket(wrapper.getQueue().poll());
			}
		} else {
			Logger.warn("Unknown wrapper!");
		}
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Wrapper wrapper = Master.getMaster().getNetworkHandler().getWrapperByHost(Master.getMaster().getNetworkHandler().getHostFromChannel(ctx.channel()));
		if (wrapper != null) {
			if (wrapper.isVerified() || packet instanceof WrapperKeyInPacket) {
				final Packet response = packet.handle(ctx.channel());
				if (response != null) ctx.channel().writeAndFlush(response);
			}
		}
	}

	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		final Wrapper wrapper = Master.getMaster().getNetworkHandler().getWrapperByHost(Master.getMaster().getNetworkHandler().getHostFromChannel(ctx.channel()));
		wrapper.disconnect();
		Logger.info("Wrapper at host " + wrapper.getWrapperMeta().getHost() + " disconnected!");
		super.channelInactive(ctx);
	}

}
