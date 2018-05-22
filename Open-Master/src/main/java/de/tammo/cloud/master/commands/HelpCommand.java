/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.master.Master;

@Command.CommandInfo(name = "help")
public class HelpCommand implements Command {

	public final boolean execute(final String[] args) {
		Master.getMaster().getLogger().info("<-- Help -->");
		Master.getMaster().getCommandHandler().getCommands().forEach(command -> {
			if (command.getClass().isAnnotationPresent(CommandInfo.class)) {
				final String name = command.getClass().getAnnotation(CommandInfo.class).name();
				if (name.equalsIgnoreCase("help")) {
					Master.getMaster().getLogger().info(name);
				}
			}
		});
		Master.getMaster().getLogger().info("");
		return false;
	}
}
