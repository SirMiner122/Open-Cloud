/*
 * Copyright (c) 2018. File created by Tammo
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
