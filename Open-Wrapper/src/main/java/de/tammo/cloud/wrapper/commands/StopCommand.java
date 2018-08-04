/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.wrapper.Wrapper;

@Command.CommandInfo(name = "stop", aliases = {"shutdown", "terminate"})
public class StopCommand implements Command {

	public boolean execute(final String[] args) {
		Wrapper.getWrapper().setRunning(false);
		return true;
	}

}
