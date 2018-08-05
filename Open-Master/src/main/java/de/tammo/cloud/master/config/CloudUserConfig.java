/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.tammo.cloud.config.DocumentFile;
import de.tammo.cloud.security.user.CloudUser;
import de.tammo.cloud.security.user.CloudUserService;

import de.tammo.cloud.service.ServiceProvider;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class CloudUserConfig extends DocumentFile {

	public CloudUserConfig() {
		super(new File("config//user.json"));
	}

	protected void load() throws IOException {
		try (final BufferedReader reader = Files.newBufferedReader(this.file.toPath())) {
			ServiceProvider.getService(CloudUserService.class).setCloudUsers(new Gson().fromJson(reader, new TypeToken<ArrayList<CloudUser>>() {}.getType()));
		}
	}

	protected void save() throws IOException {
		try (final BufferedWriter writer = Files.newBufferedWriter(this.file.toPath())) {
			final Gson gson = new GsonBuilder().setPrettyPrinting().create();
			writer.write(gson.toJson(ServiceProvider.getService(CloudUserService.class).getCloudUsers()));
		}
	}
}
