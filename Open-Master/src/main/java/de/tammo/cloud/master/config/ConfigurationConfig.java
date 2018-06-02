/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.tammo.cloud.config.DocumentFile;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.master.config.configuration.Configuration;

import java.io.*;
import java.nio.file.Files;

public class ConfigurationConfig extends DocumentFile {

	public ConfigurationConfig() {
		super(new File("config//config.json"));
	}

	protected void load() throws IOException {
		try (final BufferedReader bufferedReader = Files.newBufferedReader(this.file.toPath())) {
			Master.getMaster().setConfiguration(new Gson().fromJson(bufferedReader, Configuration.class));
		}
	}

	protected void save() throws IOException {
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.file.toPath())) {
			bufferedWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(Master.getMaster().getConfiguration()));
		}
	}

}
