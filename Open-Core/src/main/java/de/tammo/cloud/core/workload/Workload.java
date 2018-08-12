/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.workload;

import lombok.Value;

/**
 * {@link Workload} object with information about the current average usage of the system
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@Value
public class Workload {

	/**
	 * Current cpu usage in percent
	 *
	 * @since 1.0
	 */
	private final int cpu;

	/**
	 * Current usage of the memory in percent
	 *
	 * @since 1.0
	 */
	private final int memory;

}
