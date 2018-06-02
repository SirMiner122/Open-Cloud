/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests.impl;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.requests.Request;
import jline.console.ConsoleReader;

import java.io.IOException;

public class BooleanRquest extends Request {

	public BooleanRquest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	public void request(final Runnable runnable) throws IOException {
		Logger.info(this.request + " Y/N");
		if (this.reader.readLine().equalsIgnoreCase("y")) {
			runnable.run();
		}
	}

}
