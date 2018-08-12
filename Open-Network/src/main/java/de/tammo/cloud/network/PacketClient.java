/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network;

import de.tammo.cloud.core.threading.ThreadBuilder;
import de.tammo.cloud.network.packet.Packet;
import de.tammo.cloud.network.utils.ConnectableAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.net.InetSocketAddress;
import java.util.function.Consumer;
import javax.net.ssl.SSLException;
import lombok.RequiredArgsConstructor;

/**
 *
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class PacketClient {

	/**
	 * {@link ConnectableAddress} to which the {@link PacketClient} should connect
	 *
	 * @since 1.0
	 */
	private final ConnectableAddress connectableAddress;

	/**
	 * {@link EventLoopGroup} to process on more {@link Thread}s
	 *
	 * @since 1.0
	 */
	private EventLoopGroup workerGroup;

	/**
	 * {@link SslContext} to enable SSL
	 *
	 * @since 1.0
	 */
	private SslContext sslContext;

	/**
	 * {@link ChannelFuture} to close the client connection later
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
	 * Connect to a {@link PacketServer} to send {@link Packet}s
	 *
	 * @param ready {@link Runnable} which will be executed if the connection is successfully stable
	 * @param fail {@link Runnable} which will be executed if the connection failed
	 * @param init {@link Consumer} to add more handlers to the {@link PacketClient}
	 *
	 * @return Instance of this class
	 *
	 * @since 1.0
	 */
	public final PacketClient connect(final Runnable ready, final Runnable fail, final Consumer<Channel> init) {
		new ThreadBuilder("Packet-Client", () -> {
			this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

			try {
				this.future = new Bootstrap().group(this.workerGroup).channel(this.EPOLL ? EpollSocketChannel.class :
						NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

					protected void initChannel(final Channel channel) {
						if (PacketClient.this.sslContext != null)
							channel.pipeline().addLast(PacketClient.this.sslContext.newHandler(channel.alloc(),
									PacketClient.this.connectableAddress.getHost(),
									PacketClient.this.connectableAddress.getPort()));
						init.accept(channel);
					}

				}).connect(new InetSocketAddress(this.connectableAddress.getHost(), this.connectableAddress.getPort()));

				this.future.addListener((ChannelFutureListener) channelFuture -> {
					if (!channelFuture.isSuccess()) {
						fail.run();
					}
				});

				this.future.syncUninterruptibly();

				ready.run();

				this.future.channel().closeFuture().syncUninterruptibly();
			} catch (final Exception ignored) {
			} finally {
				this.workerGroup.shutdownGracefully();
			}
		}).setDaemon().startThread();
		return this;
	}

	/**
	 * Enable SSL for the {@link PacketClient}
	 *
	 * @return Instance of this class
	 *
	 * @since 1.0
	 */
	public final PacketClient withSSL() {
		try {
			this.sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} catch (SSLException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Disconnect from the currently connected {@link PacketServer}
	 *
	 * @param disconnected {@link Runnable} which will be executed, if the {@link PacketClient} is disconnected
	 *
	 * @since 1.0
	 */
	public void disconnect(final Runnable disconnected) {
		if (this.future != null && this.future.channel().isOpen()) {
			this.future.channel().close().syncUninterruptibly();
		}

		if (!this.workerGroup.isShutdown()) {
			this.workerGroup.shutdownGracefully();
		}

		disconnected.run();
	}

}
