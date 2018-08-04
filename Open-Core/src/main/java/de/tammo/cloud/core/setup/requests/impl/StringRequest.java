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
