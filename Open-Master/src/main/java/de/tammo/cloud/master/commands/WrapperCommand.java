/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.master.network.wrapper.WrapperMeta;
import de.tammo.cloud.service.ServiceProvider;
import java.util.UUID;

/**
 * Command to manage the wrappers
 *
 * @author Tammo
 * @since 1.0
 */
@Command.Info(names = "wrapper", description = "Manage the wrapper")
public class WrapperCommand implements Command {

	public boolean execute(final String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				Logger.info("<-- Wrapper -->");
				ServiceProvider.getService(NetworkProviderService.class).getWrappers().forEach(wrapper -> Logger.info(
						"Wrapper on" + " host " + wrapper.getWrapperMeta().getHost() + " with unique id " + wrapper.getWrapperMeta().getUuid().toString() + (wrapper.isConnected() ? " is connected" : " is not connected")));
				Logger.info("");
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("info")) {
				final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(args[1]);
				if (wrapper == null) {
					Logger.warn("Wrapper not available!");
				} else {
					Logger.info("Wrapper with unique id " + wrapper.getWrapperMeta().getUuid().toString() + " is " + (wrapper.isConnected() ? "connected" : "not connected"));
					return true;
				}
			} else if (args[0].equalsIgnoreCase("create")) {
				if (ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(args[1]) != null) {
					Logger.warn("Wrapper already exists!");
					return true;
				}
				final String key = ServiceProvider.getService(NetworkProviderService.class).generateWrapperKey();
				final Wrapper wrapper = new Wrapper(new WrapperMeta(UUID.randomUUID(), args[1], key));
				ServiceProvider.getService(NetworkProviderService.class).addWrapper(wrapper);
				Logger.info("Added wrapper on host " + wrapper.getWrapperMeta().getHost() + " with unique id " + wrapper.getWrapperMeta().getUuid().toString());
				Logger.info("Key for wrapper is: " + key);
				return true;
			} else if (args[0].equalsIgnoreCase("delete")) {
				final Wrapper wrapper = ServiceProvider.getService(NetworkProviderService.class).getWrapperByHost(args[1]);
				if (wrapper == null) {
					Logger.warn("Wrapper is not created yet!");
					return true;
				}
				ServiceProvider.getService(NetworkProviderService.class).removeWrapper(wrapper);
				Logger.info("Removed wrapper with host " + wrapper.getWrapperMeta().getHost() + "!");
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints the help syntax
	 */
	public void printHelp() {
		Logger.info("wrapper list");
		Logger.info("wrapper info <host>");
		Logger.info("wrapper create <host>");
		Logger.info("wrapper delete <host>");
	}
}
