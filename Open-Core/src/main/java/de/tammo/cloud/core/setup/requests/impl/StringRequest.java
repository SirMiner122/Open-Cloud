/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests.impl;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.requests.Request;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.function.Consumer;

public class StringRequest extends Request {

	public StringRequest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	public void request(final Consumer<String> callback) throws IOException {
		Logger.info(this.request);
		final String input = this.reader.readLine();
		if (input.trim().isEmpty()) {
			this.request(callback);
		} else {
			callback.accept(input);
		}
	}

}
