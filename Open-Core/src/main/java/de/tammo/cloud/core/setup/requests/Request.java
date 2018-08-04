/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.setup.requests;

import jline.console.ConsoleReader;
import lombok.RequiredArgsConstructor;

/**
 * Abstract class for implementations to request something in the setup
 *
 * @author Tammo
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class Request {

	/**
	 * Question to request
	 */
	protected final String request;

	/**
	 * {@link ConsoleReader} to read input
	 */
	protected final ConsoleReader reader;

}
