/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core;

import de.tammo.cloud.core.log.Logger;
import joptsimple.OptionSet;

/**
 * Main methods to control the {@link CloudApplication}
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public interface CloudApplication {

	/**
	 * Starts the {@link CloudApplication}
	 *
	 * @param optionSet {@link OptionSet} of given program arguments
	 *
	 * @since 1.0
	 */
	void start(final OptionSet optionSet);

	/**
	 * Shutdown the {@link CloudApplication}
	 *
	 * @since 1.0
	 */
	void shutdown();

	/**
	 * Prints the header at startup to show some information about the running program
	 *
	 * @param module Name from the module, where the header
	 *
	 * @since 1.0
	 */
	default void printHeader(final String module) {
		Logger.info("   ____                      _____ _                 _       ");
		Logger.info("  / __ \\                    / ____| |               | |     ");
		Logger.info(" | |  | |_ __   ___ _ __   | |    | | ___  _   _  __| |      ");
		Logger.info(" | |  | | '_ \\ / _ \\ '_ \\  | |    | |/ _ \\| | | |/ _` |  ");
		Logger.info(" | |__| | |_) |  __/ | | | | |____| | (_) | |_| | (_| |      ");
		Logger.info("  \\____/| .__/ \\___|_| |_|  \\_____|_|\\___/ \\__,_|\\__,_|");
		Logger.info("        | |                                                  ");
		Logger.info("        |_|                                                  ");

		this.sleep(200);

		Logger.info("");

		Logger.info("Open-Cloud");
		Logger.info("Java version " + System.getProperty("java.version") + " " + System.getProperty("os.name"));
		Logger.info("");

		this.sleep(200);

		Logger.info("Starting " + module + "!");
	}

	/**
	 * Give the program a rest
	 *
	 * @param time The time to sleep for
	 *
	 * @since 1.0
	 */
	default void sleep(final long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Version of the current program
	 *
	 * @since 1.0
	 */
	default String getVersion() {
		if (this.getClass().getPackage().getImplementationVersion() != null) {
			return this.getClass().getPackage().getImplementationVersion();
		} else {
			return "DEV VERSION";
		}
	}

}
