/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
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
