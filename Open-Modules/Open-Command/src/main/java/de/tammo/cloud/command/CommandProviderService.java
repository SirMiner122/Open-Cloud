/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.command;

import com.google.common.reflect.ClassPath;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.Service;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandProviderService implements Service {

	@Getter
	private final ArrayList<Command> commands = new ArrayList<>();

	public CommandProviderService(final String commandPackage) {
		try {
			for (final ClassPath.ClassInfo classInfo : ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(commandPackage)) {
				final Class commandClass = Class.forName(classInfo.getName());
				if (Command.class.isAssignableFrom(commandClass) && commandClass.isAnnotationPresent(Command.CommandInfo.class)) {
					this.commands.add((Command) commandClass.newInstance());
					Logger.debug("Command \"" + classInfo.getSimpleName() + "\" was added to the command list!");
				} else {
					Logger.warn("Command \"" + classInfo.getSimpleName() + "\n does not implement the Command interface or hasn't got the CommandInfo annotation!");
				}
			}
		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			Logger.error("Could not initialize commands!", e);
		}
	}

	public void executeCommand(final String message) {
		if (message.trim().isEmpty()) return;

		final AtomicBoolean found = new AtomicBoolean(false);

		this.commands.forEach(command -> {
			final Command.CommandInfo commandInfo = command.getClass().getAnnotation(Command.CommandInfo.class);
			final ArrayList<String> triggers = new ArrayList<>(Arrays.asList(commandInfo.aliases()));
			triggers.add(commandInfo.name());

			final String[] arguments = message.split(" ");

			if (triggers.stream().anyMatch(trigger -> arguments[0].equalsIgnoreCase(trigger))) {
				final String[] args = new String[arguments.length - 1];
				System.arraycopy(arguments, 1, args, 0, args.length);
				if (!command.execute(args)) command.printHelp();
				found.set(true);
			}
		});

		if (!found.get()) Logger.info("Command not found!");
	}

}
