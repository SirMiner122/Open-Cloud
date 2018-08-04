/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.bootstrap;

import de.tammo.cloud.core.exceptions.JavaVersionRequiredException;
import de.tammo.cloud.wrapper.Wrapper;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class WrapperBootstrap {

	public static void main(final String[] args) throws JavaVersionRequiredException {
		if (Double.parseDouble(System.getProperty("java.class.version")) < 52) {
			throw new JavaVersionRequiredException();
		} else {
			new WrapperBootstrap(args);
		}
	}

	private WrapperBootstrap(final String[] args) {
		final OptionParser optionParser = new OptionParser();

		optionParser.accepts("debug");
		optionParser.accepts("help");
		optionParser.accepts("version");

		optionParser.accepts("clearcache");

		final OptionSet optionSet = optionParser.parse(args);

		System.setProperty("jline.WindowsTerminal.directConsole", "false");

		new Wrapper().bootstrap(optionSet);
	}

}
