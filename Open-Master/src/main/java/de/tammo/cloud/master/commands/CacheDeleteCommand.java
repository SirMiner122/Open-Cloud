/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.master.network.packets.out.CacheDeleteOutPacket;

@Command.CommandInfo(name = "cache")
public class CacheDeleteCommand implements Command {

	public boolean execute(final String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("clear")) {
				Master.getMaster().getNetworkHandler().getWrappers().forEach(wrapper -> wrapper.sendPacket(new CacheDeleteOutPacket()));
				return true;
			}
		}
		return false;
	}

	public void printHelp() {
		Logger.info("cache clear");
	}
}
