/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.exceptions;

/**
 * This exception should be thrown, if the required Java version is not installed
 *
 * @author Tammo
 * @since 1.0
 */
public class JavaVersionRequiredException extends Exception {

	public JavaVersionRequiredException() {
		super("Java 8 is required to start the Open-Cloud!");
	}

}
