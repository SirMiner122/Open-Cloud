/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.components.server;

import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.components.ServerComponent;
import de.tammo.cloud.wrapper.network.NetworkProviderService;
import de.tammo.cloud.wrapper.network.packets.out.ServerInfoAddOutPacket;
import de.tammo.cloud.wrapper.network.packets.out.ServerInfoRemovePacket;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
public class GameServer implements ServerComponent {

	private final String serverGroup;

	private final UUID uuid;

	private final int port;

	private Process process;

	private BufferedReader reader;

	private BufferedWriter writer;

	private File workDir;

	public void start() throws IOException {
		this.workDir = new File("temp//" + this.uuid.toString());

		if (Files.notExists(this.workDir.toPath())) {
			Files.createDirectories(this.workDir.toPath());
		}

		final File globalZip = new File("cache//global.zip");

		if (Files.notExists(globalZip.toPath())) {
			this.loadGlobalTemplate();
		}

		try {
			FileUtils.unzip(globalZip.getPath(), this.workDir.getPath());
		} catch (ZipException e) {
			e.printStackTrace();
		}

		final File templateZip = new File("cache//" + this.serverGroup + ".zip");

		if (Files.notExists(templateZip.toPath())) {
			this.loadTemplate();
		}

		try {
			FileUtils.unzip(templateZip.getPath(), this.workDir.getPath());
		} catch (ZipException e) {
			e.printStackTrace();
		}

		final ProcessBuilder builder = new ProcessBuilder("java", "-jar", "server.jar", "-port", String.valueOf(this.port)).directory(this.workDir);

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
		try {
			this.dispatchCommand("stop");

			Thread.sleep(500);

			this.process.destroyForcibly();

			this.reader.close();
			this.writer.close();

			FileUtils.deleteDir(this.workDir);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void add() {
		ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new ServerInfoAddOutPacket(this.serverGroup, this.uuid, this.port));
	}

	public void remove() {
		ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new ServerInfoRemovePacket(this.uuid));
	}

	private void loadGlobalTemplate() throws IOException {
		final String url = "http://" + Wrapper.getWrapper().getConfiguration().getMasterHost() + ":" + Wrapper.getWrapper().getConfiguration().getWebPort() + "/api/v1/deployment";
		final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestProperty("type", "global");

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Files.copy(connection.getInputStream(), new File("cache//global.zip").toPath());
		} else {
			Logger.warn("Could not load global template!");
		}

		connection.disconnect();
	}

	private void loadTemplate() throws IOException {
		final String url = "http://" + Wrapper.getWrapper().getConfiguration().getMasterHost() + ":" + Wrapper.getWrapper().getConfiguration().getWebPort() + "/api/v1/deployment";
		final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestProperty("type", "template");
		connection.setRequestProperty("group", this.serverGroup);

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			Files.copy(connection.getInputStream(), new File("cache//" + this.serverGroup + ".zip").toPath());
		} else {
			Logger.warn("Could not load group template!");
		}

		connection.disconnect();
	}

}
