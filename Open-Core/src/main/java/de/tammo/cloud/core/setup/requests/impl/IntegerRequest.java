/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.setup.requests.impl;

import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.setup.requests.Request;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Implementation of the request. Request an integer
 *
 * @author Tammo
 * @since 1.0
 */
public class IntegerRequest extends Request {

	public IntegerRequest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	/**
	 * Printing the request and parse the response
	 *
	 * @param callback {@link Consumer} to get a callback with the response
	 * @throws IOException If an I/O error occurs
	 */
	public void request(final Consumer<Integer> callback) throws IOException {
		Logger.info(this.request);
		final String input = this.reader.readLine();
		if (input.trim().isEmpty()) {
			this.request(callback);
		}

		try {
			final int number = Integer.parseInt(input);
			callback.accept(number);
		} catch (NumberFormatException e) {
			this.request(callback);
		}
	}

}
