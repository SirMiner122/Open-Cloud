/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests.impl;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.requests.Request;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

public class ListRequest extends Request {

	public ListRequest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	public <T> void request(final T[] responses, final Consumer<String> accept) throws IOException {
		Logger.info(this.request + " " + this.getResponseString(responses));
		final String line = this.reader.readLine();
		if (this.contains(responses, line)) {
			accept.accept(line.toUpperCase());
		} else {
			this.request(responses, accept);
		}
	}

	private <T> String getResponseString(final T[] responses) {
		final StringBuilder responseBuilder = new StringBuilder();
		for (final T response : responses) {
			responseBuilder.append((responseBuilder.length() == 0) ? response.toString() : ", " + response.toString());
		}
		return responseBuilder.toString();
	}

	private<T> boolean contains(final T[] responses, final String response) {
		return Arrays.stream(responses).anyMatch(s -> s.toString().equalsIgnoreCase(response));
	}

}
