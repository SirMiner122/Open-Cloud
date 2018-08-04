/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.workload;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class WorkloadFactory {

	private final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public final Workload newWorkload() {
		return new Workload(this.getCpuUsage(), this.getMemory());
	}

	private int getCpuUsage() {
		return (int) (this.os.getSystemCpuLoad() * 100);
	}

	private long getUsedSystemMemory() {
		return this.os.getTotalPhysicalMemorySize() - this.os.getFreePhysicalMemorySize();
	}

	private int getMemory() {
		return (int) (((double) this.getUsedSystemMemory() / this.os.getTotalPhysicalMemorySize()) * 100);
	}

}
