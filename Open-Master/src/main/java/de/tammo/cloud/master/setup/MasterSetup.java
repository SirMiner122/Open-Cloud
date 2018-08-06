/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.setup;

import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.DownloadRequest;
import de.tammo.cloud.core.setup.requests.impl.ListRequest;
import de.tammo.cloud.master.network.NetworkProviderService;
import de.tammo.cloud.master.setup.version.ProxyVersion;
import de.tammo.cloud.master.setup.version.ServerVersion;
import de.tammo.cloud.service.ServiceProvider;
import jline.console.ConsoleReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MasterSetup implements Setup {

	public void setup(final ConsoleReader reader) throws IOException {
		final File proxyTemplate = new File("proxy");

		if (Files.notExists(proxyTemplate.toPath())) {
			Files.createDirectories(proxyTemplate.toPath());
		}

		final File proxyJar = new File("proxy//proxy.jar");
		if (Files.notExists(proxyJar.toPath())) {
			new ListRequest("Which proxy version you want to install?", reader).request(ProxyVersion.values(), version -> {
				final ProxyVersion install = ProxyVersion.valueOf(version.toUpperCase());
				try {
					Logger.info(install.getUrl());
					new DownloadRequest().request(install.getUrl(), proxyJar.getPath(), () -> Logger.info("Download complete!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		final File globalTemplate = new File("global");

		if (Files.notExists(globalTemplate.toPath())) {
			Files.createDirectories(globalTemplate.toPath());
		}

		final File serverJar = new File("global//server.jar");
		if (Files.notExists(serverJar.toPath())) {
			new ListRequest("Which server version you want to install?", reader).request(ServerVersion.values(), version -> {
				final ServerVersion install = ServerVersion.valueOf(version);
				Logger.info(install.toString());
				try {
					new ListRequest("Which specific version you want to install?", reader).request(install.getVersions(), specificVersion -> {
						try {
							Logger.info(install.getUrl().replace("%version%", install.getVersionByName(specificVersion).getUrl()));
							new DownloadRequest().request(install.getUrl().replace("%version%", install.getVersionByName(specificVersion).getUrl()), serverJar.getPath(), () -> Logger.info("Download complete!"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (ServiceProvider.getService(NetworkProviderService.class).getWrapperMetas().isEmpty()) {
			Logger.info("To create a wrapper use the following command: \"wrapper create <host>\"!");
		}
	}

}
