/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.bootstrap;

import de.tammo.cloud.core.exceptions.JavaVersionRequiredException;
import de.tammo.cloud.master.Master;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * The main class to start the Open-Master
 *
 * @author Tammo
 * @since 1.0
 */
public class MasterBootstrap {

	/**
	 * Main method which checked, if the required Java version is installed
	 *
	 * @param args Startup arguments from the virtual machine
	 * @throws JavaVersionRequiredException Throws exception if the required Java version is not installed
	 */
	public static void main(final String[] args) throws JavaVersionRequiredException {
		if (Double.parseDouble(System.getProperty("java.class.version")) < 52) {
			throw new JavaVersionRequiredException();
		} else {
			new MasterBootstrap(args);
		}
	}

	/**
	 * Bootstrap the Open-Master and check the parameter
	 *
	 * @param args Parameter to check
	 */
	private MasterBootstrap(final String[] args) {
		final OptionParser optionParser = new OptionParser();

		optionParser.accepts("debug");
		optionParser.accepts("help");
		optionParser.accepts("version");

		final OptionSet optionSet = optionParser.parse(args);

		System.setProperty("jline.WindowsTerminal.directConsole", "false");

		new Master().bootstrap(optionSet);
	}

}
