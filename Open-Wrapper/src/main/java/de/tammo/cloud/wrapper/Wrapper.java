/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper;

import de.tammo.cloud.command.CommandHandler;
import de.tammo.cloud.config.DocumentHandler;
import de.tammo.cloud.core.CloudApplication;
import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.logging.LogLevel;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.network.NettyClient;
import de.tammo.cloud.network.handler.PacketDecoder;
import de.tammo.cloud.network.handler.PacketEncoder;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.network.registry.PacketRegistry;
import de.tammo.cloud.network.utils.ConnectableAddress;
import de.tammo.cloud.wrapper.components.ServerComponentHandler;
import de.tammo.cloud.wrapper.components.proxy.ProxyServer;
import de.tammo.cloud.wrapper.components.server.GameServer;
import de.tammo.cloud.wrapper.config.configuration.Configuration;
import de.tammo.cloud.wrapper.network.NetworkHandler;
import de.tammo.cloud.wrapper.network.handler.PacketHandler;
import de.tammo.cloud.wrapper.network.packets.in.*;
import de.tammo.cloud.wrapper.network.packets.out.WrapperKeyOutPacket;
import de.tammo.cloud.wrapper.network.packets.out.WrapperWorkloadOutPacket;
import de.tammo.cloud.wrapper.setup.WrapperSetup;
import de.tammo.cloud.wrapper.workload.WorkloadProvider;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class Wrapper implements CloudApplication {

	@Getter
	private static Wrapper wrapper;

	@Getter
	private NetworkHandler networkHandler = new NetworkHandler();

	private DocumentHandler documentHandler;

	private NettyClient nettyClient;

	private WorkloadProvider workloadProvider = new WorkloadProvider();

	@Setter
	@Getter
	private Configuration configuration = new Configuration();

	@Getter
	private ServerComponentHandler serverComponentHandler;

	@Getter
	private CommandHandler commandHandler;

	@Setter
	@Getter
	private boolean running = false;

	@Setter
	private boolean verified = false;

	@Setter
	@Getter
	private String key;

	public void bootstrap(final OptionSet optionSet) {
		wrapper = this;

		this.setRunning(true);

		Logger.setPrefix("Open-Cloud Wrapper");
		Logger.setLevel(optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO);

		this.printHeader("Open-Cloud Wrapper");

		this.documentHandler = new DocumentHandler("de.tammo.cloud.wrapper.config");

		ConsoleReader reader = null;

		try {
			reader = new ConsoleReader();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final File cache = new File("cache//");
		if (cache.exists() && optionSet.has("clearcache")) {
			try {
				FileUtils.deleteDir(cache);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			new WrapperSetup().setup(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setupServer();

		while (!this.verified)

		this.serverComponentHandler = new ServerComponentHandler();

		try {
			this.serverComponentHandler.init();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.workloadProvider.start();

		this.networkHandler.startProxy();

		this.commandHandler = new CommandHandler("de.tammo.cloud.wrapper.commands");

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

	private void setupServer() {
		this.registerPackets();

		this.nettyClient = new NettyClient(new ConnectableAddress(this.configuration.getMasterHost(), this.configuration.getMasterPort())).connect(() -> Logger.info("Connected to Master!"), () -> {
			Logger.warn("Master is currently not available!");
			this.shutdown();
		}, channel -> channel.pipeline().addLast(new PacketEncoder()).addLast(new PacketDecoder()).addLast(new PacketHandler()));
	}

	public void shutdown() {
		Logger.info("Open-Cloud Wrapper is stopping!");

		this.documentHandler.saveFiles();

		this.workloadProvider.stop();

		this.serverComponentHandler.stop();

		this.nettyClient.disconnect(() -> Logger.info("Wrapper is disconnected!"));

		this.networkHandler.getExecutorService().shutdown();

		System.exit(0);
	}

	private void registerPackets() {
		PacketRegistry.PacketDirection.IN.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.IN.addPacket(201, WrapperKeyValidationInPacket.class);

		PacketRegistry.PacketDirection.IN.addPacket(300, CacheDeleteInPacket.class);
		PacketRegistry.PacketDirection.IN.addPacket(301, StartGameServerInPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(0, SuccessPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(1, ErrorPacket.class);

		PacketRegistry.PacketDirection.OUT.addPacket(200, WrapperKeyOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(202, WrapperWorkloadOutPacket.class);
	}

}
