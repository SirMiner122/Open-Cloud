/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core;

import de.tammo.cloud.core.logging.Logger;
import joptsimple.OptionSet;

public interface CloudApplication {

	void bootstrap(final OptionSet optionSet);

	void shutdown();

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

	default void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	default String getVersion() {
		if (this.getClass().getPackage().getImplementationVersion() != null) {
			return "";
		} else {
			return "DEV VERSION";
		}
	}

}
