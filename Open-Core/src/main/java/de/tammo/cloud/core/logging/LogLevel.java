/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for the log level constants
 *
 * @author Tammo
 * @since 1.0
 */
@AllArgsConstructor
public enum LogLevel {

	DEBUG(1), INFO(2), WARNING(3), ERROR(4);

	/**
	 * Int to compare the level if it is higher or lower
	 */
	@Getter
	private int level;

	/**
	 * @return Name of level. The first letter is a capital letter and the rest is lowercase
	 */
	public final String getName() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}

}
