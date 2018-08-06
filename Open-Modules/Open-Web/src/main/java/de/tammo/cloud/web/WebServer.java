/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.tammo.cloud.core.threading.ThreadBuilder;
import de.tammo.cloud.web.handler.WebRequestHandlerProvider;
import de.tammo.cloud.web.handler.WebServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

public class WebServer {

	private final int port;

	private EventLoopGroup bossGroup, workerGroup;

	private SslContext sslContext;

	public final static String URL = "/api/v1";

	public final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public WebServer(final int port, final WebRequestHandlerProvider provider) {
		this.port = port;

		new ThreadBuilder("Web Server", () -> {

			try {
				final SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
				this.sslContext = SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey()).build();
				selfSignedCertificate.delete();

				final boolean epoll = Epoll.isAvailable();

				this.bossGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
				this.workerGroup = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();

				final ChannelFuture channelFuture = new ServerBootstrap().group(this.bossGroup, this.workerGroup).channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {

					protected void initChannel(final Channel channel) {
						channel.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(Integer.MAX_VALUE), new WebServerHandler(provider));
					}

				}).bind(this.port).syncUninterruptibly();

				channelFuture.channel().closeFuture().syncUninterruptibly();
			} catch (SSLException | CertificateException e) {
				e.printStackTrace();
			} finally {
				close();
			}

		}).setDaemon().startThread();
	}

	public void close() {
		if (!this.bossGroup.isTerminated()) {
			this.bossGroup.shutdownGracefully();
		}

		if (!this.workerGroup.isTerminated()) {
			this.workerGroup.shutdownGracefully();
		}

		try {
			this.bossGroup.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			this.workerGroup.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}