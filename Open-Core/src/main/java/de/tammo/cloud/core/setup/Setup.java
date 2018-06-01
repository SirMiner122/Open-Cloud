/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup;

import jline.console.ConsoleReader;

import java.io.IOException;

public interface Setup {

	void setup(final ConsoleReader reader) throws IOException;

}
