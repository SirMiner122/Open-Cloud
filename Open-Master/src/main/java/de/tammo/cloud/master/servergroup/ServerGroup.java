/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
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
