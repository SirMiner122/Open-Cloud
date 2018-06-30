/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.network.packets.out.CacheDeleteOutPacket;

/**
 * Command to delete the cache in every Open-Wrapper
 *
 * @author Tammo
 * @version 1.0
 */
@Command.CommandInfo(name = "cache")
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
