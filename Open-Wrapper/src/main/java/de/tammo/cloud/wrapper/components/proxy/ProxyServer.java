/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.components.proxy;

import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.service.ServiceProvider;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.components.ServerComponent;
import de.tammo.cloud.wrapper.network.NetworkProviderService;
import de.tammo.cloud.wrapper.network.packets.out.ProxyInfoAddOutPacket;
import de.tammo.cloud.wrapper.network.packets.out.ProxyInfoRemoveOutPacket;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
public class ProxyServer implements ServerComponent {

	private final UUID uuid;

	private Process process;

	private BufferedReader reader;

	private BufferedWriter writer;

	private final File workDir = new File("temp//proxy//");

	public void start() throws IOException {
		Logger.info("Starting Proxy...");

		if (Files.notExists(this.workDir.toPath())) {
			Files.createDirectories(this.workDir.toPath());
		}

		final File templateZip = new File("cache//proxy.zip");

		if (Files.notExists(templateZip.toPath())) {
			this.loadTemplateFromMaster();
		}

		try {
			FileUtils.unzip(templateZip.getPath(), "temp//proxy//");
		} catch (ZipException e) {
			e.printStackTrace();
		}

		final ProcessBuilder builder = new ProcessBuilder("java", "-jar", "proxy.jar").directory(this.workDir);

		this.process = builder.start();

		this.reader = new BufferedReader(new InputStreamReader(this.process.getInputStream(), StandardCharsets.UTF_8));
		this.writer = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream(), StandardCharsets.UTF_8));
	}

	public void dispatchCommand(final String command) throws IOException {
		if (this.process == null || this.writer == null) {
			return;
		}

		this.writer.write(command);
		this.writer.newLine();
		this.writer.flush();
	}

	public void stop() {
		Logger.info("Stopping Proxy...");

		try {
			this.dispatchCommand("end");

			this.process.destroyForcibly();
			this.process.waitFor();

			this.reader.close();
			this.writer.close();

			FileUtils.deleteDir(this.workDir);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		Logger.info("Proxy was stopped!");
	}

	public void add() {
		ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new ProxyInfoAddOutPacket(this.uuid));
	}

	public void remove() {
		ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new ProxyInfoRemoveOutPacket(this.uuid));
	}

	private void loadTemplateFromMaster() throws IOException {
		final String url = "http://" + Wrapper.getWrapper().getConfiguration().getMasterHost() + ":" + Wrapper.getWrapper().getConfiguration().getWebPort() + "/api/v1/deployment";
		final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestProperty("type", "proxy");

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Files.copy(connection.getInputStream(), new File("cache//proxy.zip").toPath());
		} else {
			Logger.warn("Could not load the template!");
		}

		connection.disconnect();
	}

}