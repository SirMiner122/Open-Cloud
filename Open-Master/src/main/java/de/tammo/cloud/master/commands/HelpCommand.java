/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.*;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.service.ServiceProvider;

/**
 * Command to list all commands to get a overview
 *
 * @author Tammo
 * @since 1.0
 */
@Command.Info(names = "help", description = "Get help for all commands")
public class HelpCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public final boolean execute(final String[] args) {
		Logger.info("<-- Help -->");
		ServiceProvider.getService(CommandService.class).getCommands().values().forEach(command -> Logger.info(command.getHelp().getMainCommand()));
		Logger.info("");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandHelper getHelp() {
		return null;
	}

}
