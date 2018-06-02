/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.setup.requests;

import jline.console.ConsoleReader;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Request {

	protected final String request;

	protected final ConsoleReader reader;

}
