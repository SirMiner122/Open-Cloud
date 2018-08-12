/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.workload;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * Check the current usage of the system components
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public class WorkloadFactory {

	/**
	 * {@link OperatingSystemMXBean} to check the usages
	 *
	 * @since 1.0
	 */
	private final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	/**
	 * @return A new {@link Workload} object of the current usages
	 *
	 * @since 1.0
	 */
	public final Workload newWorkload() {
		return new Workload(this.getCpuUsage(), this.getMemory());
	}

	/**
	 * @return The current usage of the cpu in percent
	 *
	 * @since 1.0
	 */
	private int getCpuUsage() {
		return (int) (this.os.getSystemCpuLoad() * 100);
	}

	/**
	 * @return The currently used size of the memory
	 *
	 * @since 1.0
	 */
	private long getUsedSystemMemory() {
		return this.os.getTotalPhysicalMemorySize() - this.os.getFreePhysicalMemorySize();
	}

	/**
	 * @return Currently used memory in percent
	 *
	 * @since 1.0
	 */
	private int getMemory() {
		return (int) (((double) this.getUsedSystemMemory() / this.os.getTotalPhysicalMemorySize()) * 100);
	}

}
