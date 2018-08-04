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

/**
 * Implementation of the request. Request an object from a list
 *
 * @author Tammo
 * @since 1.0
 */
public class ListRequest extends Request {

	public ListRequest(final String request, final ConsoleReader reader) {
		super(request, reader);
	}

	/**
	 * Printing the request and parse the response
	 *
	 * @param responses List of possible responses
	 * @param callback {@link Consumer} to get a callback with the response
	 * @throws IOException If an I/O error occurs
	 */
	public <T> void request(final T[] responses, final Consumer<String> callback) throws IOException {
		Logger.info(this.request + " " + this.getResponseString(responses));
		final String line = this.reader.readLine();
		if (this.contains(responses, line)) {
			callback.accept(line.toUpperCase());
		} else {
			this.request(responses, callback);
		}
	}

	/**
	 * @param responses Response array of all possible responses
	 * @param <T> Type of the response array
	 * @return String from all responses separated by commas
	 */
	private <T> String getResponseString(final T[] responses) {
		final StringBuilder responseBuilder = new StringBuilder();
		for (final T response : responses) {
			responseBuilder.append((responseBuilder.length() == 0) ? response.toString() : ", " + response.toString());
		}
		return responseBuilder.toString();
	}

	/**
	 * @param responses Array of the possible responses
	 * @param response Response which should be checked, if it is in the array
	 * @param <T> Type of the response array
	 * @return The array contains the specific {@param response}
	 */
	private <T> boolean contains(final T[] responses, final String response) {
		return Arrays.stream(responses).anyMatch(s -> s.toString().equalsIgnoreCase(response));
	}

}
