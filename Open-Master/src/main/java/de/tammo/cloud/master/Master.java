/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master;

import de.tammo.cloud.command.CommandHandler;
import de.tammo.cloud.config.DocumentHandler;
import de.tammo.cloud.core.CloudApplication;
import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.logging.LogLevel;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.master.config.configuration.Configuration;
import de.tammo.cloud.master.network.NetworkHandler;
import de.tammo.cloud.master.network.handler.PacketHandler;
import de.tammo.cloud.master.network.packets.in.WrapperKeyInPacket;
import de.tammo.cloud.master.network.packets.in.WrapperWorkloadInPacket;
import de.tammo.cloud.master.network.packets.out.*;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.master.servergroup.ServerGroupHandler;
import de.tammo.cloud.master.setup.LoginSetup;
import de.tammo.cloud.master.setup.MasterSetup;
import de.tammo.cloud.master.web.TemplateDeployment;
import de.tammo.cloud.network.NettyServer;
import de.tammo.cloud.network.handler.PacketDecoder;
import de.tammo.cloud.network.handler.PacketEncoder;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.network.registry.PacketRegistry;
import de.tammo.cloud.security.user.CloudUserHandler;
import de.tammo.cloud.web.WebServer;
import de.tammo.cloud.web.handler.WebRequestHandlerProvider;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class Master implements CloudApplication {

	@Getter
	private static Master master;

	@Getter
	private NetworkHandler networkHandler;

	@Getter
	private CloudUserHandler cloudUserHandler;

	private DocumentHandler documentHandler;

	private NettyServer nettyServer;

	@Getter
	private ServerGroupHandler serverGroupHandler;

	@Getter
	private CommandHandler commandHandler;

	private WebServer webServer;

	@Setter
	@Getter
	private Configuration configuration = new Configuration();

	@Setter
	@Getter
	private boolean running = false;

	public void bootstrap(final OptionSet optionSet) {
		master = this;

		this.setRunning(true);

		Logger.setPrefix("Open-Cloud");
		Logger.setLevel(optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO);

		this.printHeader("Open-Cloud");

		this.networkHandler = new NetworkHandler();

		this.cloudUserHandler = new CloudUserHandler();

		this.serverGroupHandler = new ServerGroupHandler();

		this.documentHandler = new DocumentHandler("de.tammo.cloud.master.config");

		ConsoleReader reader = null;

		try {
			reader = new ConsoleReader(System.in, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			new LoginSetup().setup(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			new MasterSetup().setup(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.webServer = new WebServer(this.configuration.getWebPort(), new WebRequestHandlerProvider().add(new
				TemplateDeployment()).addPackage("de.tammo.cloud.master.web.rest"));

		this.setupServer(() -> Logger.info("Server was successfully bound to port " + this.configuration.getNettyPort()));

		this.commandHandler = new CommandHandler("de.tammo.cloud.master.commands");

		while (this.running) {
			try {
				this.commandHandler.executeCommand(Objects.requireNonNull(reader).readLine());
			} catch (IOException e) {
				Logger.error("Error while reading command!", e);
			}
		}

		Objects.requireNonNull(reader).close();

		this.shutdown();
	}

	private void setupServer(final Runnable ready) {
		this.registerPackets();

		this.nettyServer = new NettyServer(this.getConfiguration().getNettyPort()).bind(ready, channel -> {
			final String host = this.networkHandler.getHostFromChannel(channel);
			if (!this.networkHandler.isWhitelisted(host)) {
				channel.close().syncUninterruptibly();
				Logger.warn("A not whitelisted Wrapper would like to connect to this master!");
				return;
			}

			channel.pipeline().addLast(new PacketEncoder()).addLast(new PacketDecoder()).addLast(new PacketHandler());
			final Wrapper wrapper = this.networkHandler.getWrapperByHost(host);
			if (wrapper != null) {
				wrapper.setChannel(channel);
				Logger.info("Wrapper from " + wrapper.getWrapperMeta().getHost() + " connected!");
			}
		});
	}

	public void shutdown() {
		Logger.info("Open-Cloud is stopping!");

		this.documentHandler.saveFiles();

		this.webServer.close();

		this.networkHandler.getWrappers().stream().filter(Wrapper::isConnected).forEach(wrapper -> wrapper.getChannel().close().syncUninterruptibly());

		this.nettyServer.close(() -> Logger.info("Netty server was closed!"));

		final File temp = new File("temp");
		if (Files.exists(temp.toPath())) {
			try {
				FileUtils.deleteDir(temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	private void registerPackets() {
		PacketRegistry.PacketDirection.IN.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.IN.addPacket(200, WrapperKeyInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(202, WrapperWorkloadInPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(201, WrapperKeyValidationOutPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(300, CacheDeleteOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(301, StartGameServerOutPacket.class);
	}

}
