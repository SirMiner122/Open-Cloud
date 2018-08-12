/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.command.CommandHelper;
import de.tammo.cloud.wrapper.Wrapper;

@Command.Info(names = {"stop", "shutdown", "terminate"}, description = "Stops the wrapper")
public class StopCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(final String[] args) {
		Wrapper.getWrapper().setRunning(false);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public final CommandHelper getHelp() {
		return new CommandHelper("Stop");
	}

}
