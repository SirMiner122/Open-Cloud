/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper;

import de.tammo.cloud.command.CommandProviderService;
import de.tammo.cloud.config.DocumentProviderService;
import de.tammo.cloud.core.CloudApplication;
import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.log.LogLevel;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.network.NettyClient;
import de.tammo.cloud.network.handler.PacketDecoder;
import de.tammo.cloud.network.handler.PacketEncoder;
import de.tammo.cloud.network.packet.impl.ErrorPacket;
import de.tammo.cloud.network.packet.impl.SuccessPacket;
import de.tammo.cloud.network.registry.PacketRegistry;
import de.tammo.cloud.network.utils.ConnectableAddress;
import de.tammo.cloud.wrapper.components.ServerComponentProviderService;
import de.tammo.cloud.wrapper.config.configuration.Configuration;
import de.tammo.cloud.wrapper.network.NetworkProviderService;
import de.tammo.cloud.wrapper.network.handler.PacketHandler;
import de.tammo.cloud.wrapper.network.packets.in.*;
import de.tammo.cloud.wrapper.network.packets.out.*;
import de.tammo.cloud.wrapper.setup.WrapperSetup;
import de.tammo.cloud.wrapper.workload.WorkloadProviderService;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Wrapper implements CloudApplication {

	@Getter
	private static Wrapper wrapper;

	private NettyClient nettyClient;

	@Setter
	@Getter
	private Configuration configuration = new Configuration();

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

		ServiceProvider.addService(new DocumentProviderService("de.tammo.cloud.wrapper.config"));

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

		while (!this.verified) {}

		ServiceProvider.addService(new NetworkProviderService());
		ServiceProvider.addService(new ServerComponentProviderService());
		ServiceProvider.addService(new WorkloadProviderService());
		ServiceProvider.addService(new CommandProviderService("de.tammo.cloud.wrapper.commands"));

		ServiceProvider.init();

		while (this.running) {
			try {
				ServiceProvider.getService(CommandProviderService.class).executeCommand(Objects.requireNonNull(reader).readLine());
			} catch (IOException e) {
				Logger.error("An error occured while reading from commandline!", e);
			}
		}

		Objects.requireNonNull(reader).close();

		this.shutdown();
	}

	private void setupServer() {
		this.registerPackets();

		this.nettyClient = new NettyClient(new ConnectableAddress(this.configuration.getMasterHost(), this.configuration.getMasterPort())).connect(() -> Logger.info("Successfully connected to the Master!"), () -> {
			Logger.warn("Master is currently not available!");
			this.shutdown();
		}, channel -> channel.pipeline().addLast(new PacketEncoder()).addLast(new PacketDecoder()).addLast(new PacketHandler()));
	}

	public void shutdown() {
		Logger.info("Open-Cloud Wrapper is stopping!");

		ServiceProvider.getService(DocumentProviderService.class).saveFiles();
		ServiceProvider.getService(WorkloadProviderService.class).stop();
		ServiceProvider.getService(ServerComponentProviderService.class).stop();

		this.nettyClient.disconnect(() -> Logger.info("Wrapper is disconnected!"));

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

		PacketRegistry.PacketDirection.OUT.addPacket(400, ProxyInfoAddOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(401, ProxyInfoRemoveOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(402, ServerInfoAddOutPacket.class);
		PacketRegistry.PacketDirection.OUT.addPacket(403, ServerInfoRemovePacket.class);
	}

}
