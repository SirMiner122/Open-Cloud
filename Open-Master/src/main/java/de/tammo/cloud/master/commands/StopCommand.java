/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.commands;

import de.tammo.cloud.command.Command;
import de.tammo.cloud.master.Master;

/**
 * Command to stop the Open-Master
 *
 * @author Tammo
 * @version 1.0
 */
@Command.CommandInfo(name = "stop", aliases = {"shutdown", "terminate"})
public class StopCommand implements Command {

	public boolean execute(final String[] args) {
		Master.getMaster().setRunning(false);
		return true;
	}
}
