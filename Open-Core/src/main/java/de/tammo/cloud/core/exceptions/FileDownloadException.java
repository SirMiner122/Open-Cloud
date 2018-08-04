/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.exceptions;

/**
 * This exception should be thrown, if the download of a file failed
 *
 * @author Tammo
 * @since 1.0
 */
public class FileDownloadException extends Exception {

	public FileDownloadException(final String message) {
		super(message);
	}

}
