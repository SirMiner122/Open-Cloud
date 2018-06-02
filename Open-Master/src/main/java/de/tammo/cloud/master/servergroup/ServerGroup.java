/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.servergroup;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Data
@AllArgsConstructor
public class ServerGroup {

	private final String name;

	private int minServer;

	private int maxServer;

	private int minRam;

	private int maxRam;

	public void init() {
		final File template = new File("template//" + this.getName());
		if (Files.notExists(template.toPath())) {
			try {
				Files.createDirectories(template.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
