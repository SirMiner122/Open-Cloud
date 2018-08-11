/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.command.CommandService;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.service.ServiceProvider;
import java.util.Arrays;

/**
 * Command to list all commands to get a overview
 *
 * @author Tammo
 * @since 1.0
 */
@Command.Info(names = "help")
public class HelpCommand implements Command {

	public final boolean execute(final String[] args) {
		Logger.info("<-- Help -->");
		/*ServiceProvider.getService(CommandService.class).getCommands().forEach(command -> {
			if (command.getClass().isAnnotationPresent(Info.class)) {
				final String[] names = command.getClass().getAnnotation(Info.class).name();
				if (!Arrays.asList(names).contains("help")) {
					Arrays.stream(names).forEach(Logger::info);
				}
			}
		});
		Logger.info("");*/
		return false;
	}
}
