/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.tammo.cloud.config.DocumentFile;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.security.user.CloudUser;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class CloudUserConfig extends DocumentFile {

	public CloudUserConfig() {
		super(new File("config//user.json"));
	}

	protected void load() throws IOException {
		try (final BufferedReader reader = Files.newBufferedReader(this.file.toPath())) {
			Master.getMaster().getCloudUserHandler().setCloudUsers(new Gson().fromJson(reader, new TypeToken<ArrayList<CloudUser>>() {}.getType()));
		}
	}

	protected void save() throws IOException {
		try (final BufferedWriter writer = Files.newBufferedWriter(this.file.toPath())) {
			final Gson gson = new GsonBuilder().setPrettyPrinting().create();
			writer.write(gson.toJson(Master.getMaster().getCloudUserHandler().getCloudUsers()));
		}
	}
}
