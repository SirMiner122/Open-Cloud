/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.command;

/**
 * Interface to implement auto completion
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public interface AutoComplete {

	/**
	 * @param start Current typed word
	 * @param index Current argument index
	 *
	 * @return Result from the completion
	 *
	 * @since 1.0
	 */
	String complete(final String start, final int index);

}
