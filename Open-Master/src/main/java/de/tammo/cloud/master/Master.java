/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master;

import de.tammo.cloud.command.CommandProviderService;
import de.tammo.cloud.config.DocumentProviderService;
import de.tammo.cloud.core.CloudApplication;
import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.logging.LogLevel;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.components.ComponentsProviderService;
import de.tammo.cloud.master.config.configuration.Configuration;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.handler.PacketHandler;
import de.tammo.cloud.master.network.packets.in.*;
import de.tammo.cloud.master.network.packets.out.*;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.master.servergroup.ServerGroupService;
import de.tammo.cloud.master.setup.LoginSetup;
import de.tammo.cloud.master.setup.MasterSetup;
import de.tammo.cloud.master.web.TemplateDeployment;
import de.tammo.cloud.network.NettyServer;
import de.tammo.cloud.network.handler.PacketDecoder;
import de.tammo.cloud.network.handler.PacketEncoder;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.network.registry.PacketRegistry;
import de.tammo.cloud.security.user.CloudUserService;
import de.tammo.cloud.web.WebServer;
import de.tammo.cloud.web.handler.WebRequestHandlerProvider;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * Main class to control everything
 *
 * @author Tammo
 * @version 1.0
 */
public class Master implements CloudApplication {

	/**
	 * Singleton instance of {@link Master}
	 */
	@Getter
	private static Master master;

	/**
	 * Instance of {@link NettyServer}
	 */
	private NettyServer nettyServer;

	/**
	 * Instance of {@link WebServer}
	 */
	private WebServer webServer;

	/**
	 * {@link Configuration} of the current runtime
	 */
	@Setter
	@Getter
	private Configuration configuration = new Configuration();

	/**
	 * Open-Master is actually running
	 */
	@Setter
	@Getter
	private boolean running = false;

	/**
	 * Bootstrapping the Open-Master
	 *
	 * @param optionSet Parsed arguments to configure the runtime
	 */
	public void bootstrap(final OptionSet optionSet) {
		master = this;

		this.setRunning(true);

		Logger.setPrefix("Open-Cloud");
		Logger.setLevel(optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO);

		this.printHeader("Open-Cloud");

		ServiceProvider.addService(new ComponentsProviderService());
		ServiceProvider.addService(new NetworkProviderService());
		ServiceProvider.addService(new CloudUserService());
		ServiceProvider.addService(new ServerGroupService());
		ServiceProvider.addService(new DocumentProviderService("de.tammo.cloud.master.config"));
		ServiceProvider.addService(new CommandProviderService("de.tammo.cloud.master.commands"));

		ServiceProvider.init();

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

		while (this.running) {
			try {
				ServiceProvider.getService(CommandProviderService.class).executeCommand(Objects.requireNonNull(reader).readLine());
			} catch (IOException e) {
				Logger.error("Error while reading command!", e);
			}
		}

		Objects.requireNonNull(reader).close();

		this.shutdown();
	}

	/**
	 * Setting up the server to receive packets
	 *
	 * @param ready Runnable which where executed, when the server was initialised
	 */
	private void setupServer(final Runnable ready) {
		this.registerPackets();

		final NetworkProviderService networkProviderService = ServiceProvider.getService(NetworkProviderService.class);

		this.nettyServer = new NettyServer(this.getConfiguration().getNettyPort()).bind(ready, channel -> {
			final String host = networkProviderService.getHostFromChannel(channel);
			if (!networkProviderService.isWhitelisted(host)) {
				channel.close().syncUninterruptibly();
				Logger.warn("A not whitelisted Wrapper would like to connect to this master!");
				return;
			}

			channel.pipeline().addLast(new PacketEncoder()).addLast(new PacketDecoder()).addLast(new PacketHandler());
			final Wrapper wrapper = networkProviderService.getWrapperByHost(host);
			if (wrapper != null) {
				wrapper.setChannel(channel);
				Logger.info("Wrapper from " + wrapper.getWrapperMeta().getHost() + " connected!");
			}
		});
	}

	/**
	 * Stop the whole process
	 */
	public void shutdown() {
		Logger.info("Open-Cloud is stopping!");

		ServiceProvider.getService(DocumentProviderService.class).saveFiles();

		this.webServer.close();

		ServiceProvider.getService(NetworkProviderService.class).getWrappers().stream().filter(Wrapper::isConnected)
				.forEach(wrapper -> wrapper
				.getChannel()
				.close()
				.syncUninterruptibly());

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

	/**
	 * Register all packets in the {@link PacketRegistry}
	 */
	private void registerPackets() {
		PacketRegistry.PacketDirection.IN.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.IN.addPacket(200, WrapperKeyInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(202, WrapperWorkloadInPacket.class);

		PacketRegistry.PacketDirection.IN.addPacket(400, ProxyInfoAddInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(401, ProxyInfoRemoveInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(402, ServerInfoAddInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(403, ServerInfoRemoveInPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(201, WrapperKeyValidationOutPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(300, CacheDeleteOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(301, StartGameServerOutPacket.class);
	}

}
