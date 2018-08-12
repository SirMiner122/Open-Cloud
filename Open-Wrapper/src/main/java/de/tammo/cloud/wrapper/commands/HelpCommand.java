/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.commands;

import de.tammo.cloud.command.*;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.service.ServiceProvider;

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
