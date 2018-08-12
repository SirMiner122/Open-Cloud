/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network;

import de.tammo.cloud.core.threading.ThreadBuilder;
import de.tammo.cloud.network.packet.Packet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.security.cert.CertificateException;
import java.util.function.Consumer;
import javax.net.ssl.SSLException;
import lombok.RequiredArgsConstructor;

/**
 * {@link PacketServer} to receive {@link Packet}s and to process them
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class PacketServer {

	/**
	 * Port on which the {@link PacketServer} should receive the {@link Packet}s
	 *
	 * @since 1.0
	 */
	private final int port;

	/**
	 * {@link EventLoopGroup}s to process the {@link Packet}s with more {@link Thread}s
	 *
	 * @since 1.0
	 */
	private EventLoopGroup bossGroup, workerGroup;

	/**
	 * {@link SslContext} to enable ssl at the {@link PacketServer}
	 *
	 * @since 1.0
	 */
	private SslContext sslContext;

	/**
	 * {@link ChannelFuture} to close the {@link PacketServer} later
	 *
	 * @since 1.0
	 */
	private ChannelFuture future;

	/**
	 * Constant boolean to check, if Epoll is available
	 *
	 * @since 1.0
	 */
	private final boolean EPOLL = Epoll.isAvailable();

	/**
	 * Bind the {@link PacketServer} to the {@link PacketServer#port} to receive {@link Packet}s
	 *
	 * @param ready {@link Runnable} which will be executed, if the {@link PacketServer} was bounded to the port
	 * @param init {@link Consumer} to allow to add more handler to the {@link PacketServer}
	 *
	 * @return Instance of this class
	 *
	 * @since 1.0
	 */
	public final PacketServer bind(final Runnable ready, final Consumer<Channel> init) {
		new ThreadBuilder("Packet-Server", () -> {
			this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
			this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

			try {
				final ChannelFuture future = new ServerBootstrap()
						.group(this.bossGroup, this.workerGroup)
						.channel(this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
						.childHandler(new ChannelInitializer<Channel>() {

					protected void initChannel(final Channel channel) {
						if (PacketServer.this.sslContext != null) channel.pipeline().addLast(PacketServer.this.sslContext.newHandler(channel.alloc()));
						init.accept(channel);
					}

				}).bind(this.port).syncUninterruptibly();

				ready.run();

				PacketServer.this.future = future;

				future.channel().closeFuture().syncUninterruptibly();
			} finally {
				this.workerGroup.shutdownGracefully();
				this.bossGroup.shutdownGracefully();
			}
		}).setDaemon().startThread();
		return this;
	}

	/**
	 * Create a {@link SelfSignedCertificate} to enable SSL for this {@link PacketServer}
	 *
	 * @return Instance of this class
	 *
	 * @since 1.0
	 */
	public final PacketServer withSSL() {
		try {
			final SelfSignedCertificate certificate = new SelfSignedCertificate();
			this.sslContext = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build();
			certificate.delete();
		} catch (CertificateException | SSLException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Close this {@link PacketServer} to shutdown
	 *
	 * @since 1.0
	 */
	public void close() {
		if (this.future.channel().isOpen()) {
			this.future.channel().close().syncUninterruptibly();
		}
		this.workerGroup.shutdownGracefully();
		this.bossGroup.shutdownGracefully();
	}

}
