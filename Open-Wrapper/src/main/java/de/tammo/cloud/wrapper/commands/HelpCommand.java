/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.wrapper.Wrapper;

@Command.CommandInfo(name = "help")
public class HelpCommand implements Command {

	public boolean execute(final String[] args) {
		Wrapper.getWrapper().getLogger().info("<-- Help -->");
		Wrapper.getWrapper().getCommandHandler().getCommands().forEach(command -> {
			if (command.getClass().isAnnotationPresent(CommandInfo.class)) {
				final String name = command.getClass().getAnnotation(CommandInfo.class).name();
				if (name.equalsIgnoreCase("help")) {
					Wrapper.getWrapper().getLogger().info(name);
				}
			}
		});
		Wrapper.getWrapper().getLogger().info("");
		return false;
	}

}
