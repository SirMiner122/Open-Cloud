/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.tammo.cloud.config.DocumentFile;
import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.master.servergroup.ServerGroup;
import de.tammo.cloud.master.servergroup.ServerGroupService;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class ServerGroupConfig extends DocumentFile {

	public ServerGroupConfig() {
		super(new File("config//servergroups.json"));
	}

	protected void load() throws IOException {
		try (final BufferedReader bufferedReader = Files.newBufferedReader(this.file.toPath())){
			ServiceProvider.getService(ServerGroupService.class).setServerGroups(new Gson().fromJson(bufferedReader, new TypeToken<ArrayList<ServerGroup>>(){}.getType()));
		}
	}

	protected void save() throws IOException {
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.file.toPath())){
			bufferedWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(ServiceProvider.getService(ServerGroupService.class).getServerGroups()));
		}
	}
}
