/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests;

import de.tammo.cloud.core.logging.Logger;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.function.Consumer;

public class IntegerRequest {

	public void request(final Logger logger, final String request, final ConsoleReader reader, final Consumer<Integer> accept) throws IOException {
		logger.info(request);
		final String input = reader.readLine();
		if (input.trim().isEmpty()) {
			this.request(logger, request, reader, accept);
		}

		try {
			final int number = Integer.parseInt(input);
			accept.accept(number);
		} catch (NumberFormatException e) {
			this.request(logger, request, reader, accept);
		}
	}

}
