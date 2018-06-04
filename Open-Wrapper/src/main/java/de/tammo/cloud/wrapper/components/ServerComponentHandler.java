/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.components;

import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.threading.ThreadBuilder;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.components.proxy.ProxyServer;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;

public class ServerComponentHandler implements Runnable{

	@Getter
	private final File cache = new File("cache");

	@Getter
	private final File temp = new File("temp");

	private Thread thread;

	private final Queue<ServerComponent> componentQueue = new ConcurrentLinkedQueue<>();

	private final CopyOnWriteArrayList<ServerComponent> components = new CopyOnWriteArrayList<>();

	private final ExecutorService executorService = Executors.newCachedThreadPool();

	private final ProxyServer proxyServer = new ProxyServer(UUID.randomUUID());

	public void init() throws IOException {
		if (Files.notExists(this.cache.toPath())) {
			Files.createDirectories(this.cache.toPath());
		}

		this.startProxy();

		this.thread = new ThreadBuilder("Component-Queue", this).setDaemon().start();
	}

	private void startProxy() {
		this.offerServerComponent(this.proxyServer);
	}

	public void run() {
		while (Wrapper.getWrapper().isRunning()) {
			if (!this.componentQueue.isEmpty()) {
				final ServerComponent component = this.componentQueue.poll();
				if (component != null) {
					this.startServerComponent(component);
				}
			}
		}
	}

	public void stop() {
		this.components.forEach(this::stopServerComponent);

		if (!this.executorService.isTerminated()) {
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.thread.interrupt();

		if (Files.exists(this.temp.toPath())) {
			try {
				FileUtils.deleteDir(this.temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void offerServerComponent(final ServerComponent server) {
		this.componentQueue.offer(server);
	}

	private void startServerComponent(final ServerComponent server) {
		this.executorService.submit(() -> {
			try {
				this.components.add(server);
				server.add();
				server.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void stopServerComponent(final ServerComponent server) {
		this.executorService.submit(() -> {
			server.stop();
			server.remove();
			this.components.remove(server);
		});
	}

}