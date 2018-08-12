/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.threading;

/**
 * Builder util to start and build {@link Thread}s easily
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public class ThreadBuilder {

	/**
	 * {@link Thread} object to build and configure
	 */
	private final Thread thread;

	/**
	 * Create a new {@link ThreadBuilder}
	 *
	 * @param name Name of the {@link Thread}
	 * @param runnable {@link Runnable} to run in the new {@link Thread}
	 *
	 * @since 1.0
	 */
	public ThreadBuilder(final String name, final Runnable runnable) {
		this.thread = new Thread(runnable, name);
	}

	/**
	 * @return The current instance after configure the {@link Thread} as daemon
	 *
	 * @since 1.0
	 */
	public final ThreadBuilder setDaemon() {
		this.thread.setDaemon(true);
		return this;
	}

	/**
	 * Start {@link Thread} without a return value
	 *
	 * @since 1.0
	 */
	public void startThread() {
		this.thread.start();
	}

	/**
	 * @return The builded {@link Thread} and starts the {@link Thread}
	 *
	 * @since 1.0
	 */
	public final Thread start() {
		this.thread.start();
		return this.thread;
	}

}