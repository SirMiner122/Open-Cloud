/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.tammo.cloud.config.DocumentFile;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.config.configuration.Configuration;

import java.io.*;
import java.nio.file.Files;

public class ConfigurationConfig extends DocumentFile {

	public ConfigurationConfig() {
		super(new File("config//configuration.json"));
	}

	protected void load() throws IOException {
		try (final BufferedReader reader = Files.newBufferedReader(this.file.toPath())) {
			Wrapper.getWrapper().setConfiguration(new Gson().fromJson(reader, Configuration.class));
		}
	}

	protected void save() throws IOException {
		try (final BufferedWriter writer = Files.newBufferedWriter(this.file.toPath())) {
			writer.write(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(Wrapper.getWrapper().getConfiguration()));
		}
	}
}
