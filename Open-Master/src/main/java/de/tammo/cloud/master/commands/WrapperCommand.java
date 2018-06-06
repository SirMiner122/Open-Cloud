/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.master.network.wrapper.Wrapper;
import de.tammo.cloud.master.network.wrapper.WrapperMeta;

import java.util.UUID;

@Command.CommandInfo(name = "wrapper", aliases = {"w"})
public class WrapperCommand implements Command {

	public boolean execute(final String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				Logger.info("<-- Wrapper -->");
				Master.getMaster().getNetworkHandler().getWrappers().forEach(wrapper -> Logger.info("Wrapper at host " + wrapper.getWrapperMeta().getHost() + " with unique id " + wrapper.getWrapperMeta().getUuid().toString() + (wrapper.isConnected() ? " is currently connected" : " is not connected")));
				Logger.info("");
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("info")) {
				final Wrapper wrapper = Master.getMaster().getNetworkHandler().getWrapperByHost(args[1]);
				if (wrapper == null) {
					Logger.warn("Wrapper not available!");
				} else {
					 Logger.info("Wrapper with unique id " + wrapper.getWrapperMeta().getUuid().toString() + " is currently " + (wrapper.isConnected() ? "connected" : "not connected"));
					return true;
				}
			} else if (args[0].equalsIgnoreCase("create")) {
				if (Master.getMaster().getNetworkHandler().getWrapperByHost(args[1]) != null) {
					Logger.warn("A Wrapper called \"" + args[1] +"\" already exists!");
					return true;
				}
				final String key = Master.getMaster().getNetworkHandler().generateWrapperKey();
				final Wrapper wrapper = new Wrapper(new WrapperMeta(UUID.randomUUID(), args[1], key));
				Master.getMaster().getNetworkHandler().addWrapper(wrapper);
				Logger.info("Added wrapper at host " + wrapper.getWrapperMeta().getHost() + " with unique id " + wrapper.getWrapperMeta().getUuid().toString());
				Logger.info("Key for wrapper is: " + key);
				return true;
			} else if (args[0].equalsIgnoreCase("delete")) {
				final Wrapper wrapper = Master.getMaster().getNetworkHandler().getWrapperByHost(args[1]);
				if (wrapper == null) {
					Logger.warn("There is no Wrapper at the specified host");
					return true;
				}
				Master.getMaster().getNetworkHandler().removeWrapper(wrapper);
				Logger.info("Removed wrapper at host " + wrapper.getWrapperMeta().getHost() + "!");
				return true;
			}
		}
		return false;
	}

	public void printHelp() {
		Logger.info("wrapper list");
		Logger.info("wrapper info <host>");
		Logger.info("wrapper create <host>");
		Logger.info("wrapper delete <host>");
	}
}
