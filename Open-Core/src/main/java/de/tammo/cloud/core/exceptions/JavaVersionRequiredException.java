/*
 * Copyright (c) 2018. File created by Tammo
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
