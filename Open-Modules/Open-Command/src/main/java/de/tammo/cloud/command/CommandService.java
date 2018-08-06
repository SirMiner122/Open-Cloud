/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.command;

import com.google.common.reflect.ClassPath;
import de.tammo.cloud.service.Service;
import java.io.IOException;
import java.util.*;

/**
 * Configure commands and execute them
 *
 * @author Tammo, x7Airworker
 * @version 2.0
 * @since 1.0
 */
public class CommandService implements Service {

	/**
	 * {@link Map} to hold all {@link Command}s with the trigger as key and the {@link Command} object as value
	 *
	 * @since 2.0
	 */
	private final Map<String, Command> commands = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	public void init() {
		try {
			ClassPath.from(this.getClass().getClassLoader()).getAllClasses()
					.stream()
					.map(ClassPath.ClassInfo::load)
					.filter(aClass -> aClass.isAssignableFrom(Command.class))
					.filter(aClass -> aClass.isAnnotationPresent(Command.Info.class))
					.forEach(aClass -> {
						try {
							final Command command = (Command) aClass.newInstance();
							Arrays.stream(command.getInfo().names()).forEach(name -> this.commands.put(name, command));
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute a {@link Command}
	 *
	 * @param message Message to parse for the {@link Command}s
	 *
	 * @since 1.0
	 */
	public void executeCommand(final String message) {
		final String[] arguments = message.split("\\s+");

		final Command command = this.commands.get(arguments[0]);
		if(command == null) return;

		final String[] newArgs = new String[arguments.length - 1];
		System.arraycopy(arguments, 1, newArgs, 0, newArgs.length);

		command.execute(newArgs);
	}

	/**
	 * Auto complete a command
	 *
	 * @param message Current typed message to complete
	 *
	 * @since 2.0
	 */
	public void completeCommand(final String message) {
		final String[] arguments = message.split("\\s+");

		final Command command = this.commands.get(arguments[0]);
		if(command == null || !command.getClass().isAssignableFrom(AutoComplete.class)) return;

		final AutoComplete autoComplete = (AutoComplete) command;
		final String begin = arguments[arguments.length - 1];

		autoComplete.complete(begin, arguments.length - 1);
	}

}
