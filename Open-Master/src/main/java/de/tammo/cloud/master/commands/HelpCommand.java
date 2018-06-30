/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.command.CommandProviderService;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.ServiceProvider;

/**
 * Command to list all commands to get a overview
 *
 * @author Tammo
 * @version 1.0
 */
@Command.CommandInfo(name = "help")
public class HelpCommand implements Command {

	public final boolean execute(final String[] args) {
		Logger.info("<-- Help -->");
		ServiceProvider.getService(CommandProviderService.class).getCommands().forEach(command -> {
			if (command.getClass().isAnnotationPresent(CommandInfo.class)) {
				final String name = command.getClass().getAnnotation(CommandInfo.class).name();
				if (!name.equalsIgnoreCase("help")) {
					Logger.info(name);
				}
			}
		});
		Logger.info("");
		return false;
	}
}
