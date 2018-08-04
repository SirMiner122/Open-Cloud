/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.setup.requests.impl;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.requests.Request;
import jline.console.ConsoleReader;

import java.io.IOException;

/**
 * Implementation of the request. Request a boolean state
 *
 * @author Tammo
 * @since 1.0
 */
public class BooleanRequest extends Request {

	public BooleanRequest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	/**
	 * Printing the request and parse the response
	 *
	 * @param runnable {@link Runnable} which will be executed, if the response of the request is true
	 * @throws IOException If an I/O error occurs
	 */
	public void request(final Runnable runnable) throws IOException {
		Logger.info(this.request + " Y/N");
		if (this.reader.readLine().equalsIgnoreCase("y")) {
			runnable.run();
		}
	}

}
