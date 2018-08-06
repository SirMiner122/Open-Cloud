/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.packets.out.CacheDeleteOutPacket;
import de.tammo.cloud.service.ServiceProvider;

/**
 * Command to delete the cache in every Open-Wrapper
 *
 * @author Tammo
 * @since 1.0
 */
@Command.Info(names = "cache")
public class CacheDeleteCommand implements Command {

	public boolean execute(final String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("clear")) {
				ServiceProvider.getService(NetworkProviderService.class).getWrappers().forEach(wrapper -> wrapper.sendPacket(new CacheDeleteOutPacket()));
				return true;
			}
		}
		return false;
	}

	/**
	 * Prints the help syntax
	 */
	public void printHelp() {
		Logger.info("cache clear");
	}

}
