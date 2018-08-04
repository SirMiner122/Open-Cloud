/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.threading;

public class ThreadBuilder {

	private final Thread thread;

	public ThreadBuilder(final String name, final Runnable runnable) {
		this.thread = new Thread(runnable, name);
	}

	public final ThreadBuilder setDaemon() {
		this.thread.setDaemon(true);
		return this;
	}

	public void startThread() {
		this.thread.start();
	}

	public final Thread start() {
		this.thread.start();
		return this.thread;
	}

}