/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.setup;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.*;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.master.setup.version.ProxyVersion;
import de.tammo.cloud.master.setup.version.ServerVersion;
import jline.console.ConsoleReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

public class MasterSetup implements Setup {

	public void setup(final Logger logger, final ConsoleReader reader) throws IOException {
		if (Files.notExists(Master.getMaster().getTemplateHandler().getProxyTemplate().toPath())) {
			Files.createDirectories(Master.getMaster().getTemplateHandler().getProxyTemplate().toPath());
		}

		final File proxyJar = new File("proxy//proxy.jar");
		if (Files.notExists(proxyJar.toPath())) {
			new ListRequest().request(logger, "Which proxy version you want to install?", ProxyVersion.values(),
					reader, version -> {
						final ProxyVersion install = ProxyVersion.valueOf(version.toUpperCase());
						try {
							logger.info(install.getUrl());
							new DownloadRequest().request(logger, install.getUrl(), proxyJar.getPath(), () -> logger.info("Download complete!"));
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
		}

		if (Files.notExists(Master.getMaster().getTemplateHandler().getGlobalTemplate().toPath())) {
			Files.createDirectories(Master.getMaster().getTemplateHandler().getGlobalTemplate().toPath());
		}

		final File serverJar = new File("global//server.jar");
		if (Files.notExists(serverJar.toPath())) {
			new ListRequest().request(logger, "Which server version you want to install?", ServerVersion.values(),
					reader, version -> {
						final ServerVersion install = ServerVersion.valueOf(version);
						logger.info(install.toString());
						try {
							new ListRequest().request(logger, "Which specific version you want to install?", install
									.getVersions(), reader, specificVersion -> {
								try {
									logger.info(install.getUrl().replace("%version%", install.getVersionByName
											(specificVersion).getUrl()));
									new DownloadRequest().request(logger, install.getUrl().replace("%version%",
											install.getVersionByName(specificVersion).getUrl()), serverJar.getPath(), () ->
											logger.info
											("Download complete!"));
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
		}

		if (Master.getMaster().getNetworkHandler().getWrapperMetas().isEmpty()) {
			logger.info("To create a wrapper use the following command: \"wrapper create <host>\"!");
		}
	}

}
