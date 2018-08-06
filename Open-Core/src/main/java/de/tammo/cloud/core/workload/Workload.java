/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.workload;

import lombok.Value;

@Value
public class Workload {

	private final int cpu;

	private final int memory;

}
