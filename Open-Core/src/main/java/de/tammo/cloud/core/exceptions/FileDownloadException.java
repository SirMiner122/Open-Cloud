/*
 * Copyright (c) 2018. File created by Tammo
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
