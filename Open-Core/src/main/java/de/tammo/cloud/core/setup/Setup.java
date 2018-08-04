/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup;

import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Interface which can be implemented for setups
 *
 * @author Tammo
 * @since 1.0
 */
public interface Setup {

	/**
	 * Setup the running programm
	 *
	 * @param reader {@link ConsoleReader} to read input
	 * @throws IOException Reading of input has failed
	 */
	void setup(final ConsoleReader reader) throws IOException;

}
