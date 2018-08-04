/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.setup.version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum ServerVersion {

	SPIGOT("https://yivesmirror.com/files/spigot/%version%", new Version[] {
			new Version("1.7.10", "spigot-1.7.10-SNAPSHOT-b1657.jar"),
			new Version("1.8", "spigot-1.8-R0.1-SNAPSHOT.jar"),
			new Version("1.8.3", "spigot-1.8.3-R0.1-SNAPSHOT.jar"),
			new Version("1.8.8", "spigot-1.8.8-R0.1-SNAPSHOT.jar"),
			new Version("1.9.4", "spigot-1.9.4-R0.1-SNAPSHOT.jar"),
			new Version("1.10.2", "spigot-1.10.2-R0.1-SNAPSHOT.jar"),
			new Version("1.11.2", "spigot-1.11.2-R0.1-SNAPSHOT.jar"),
			new Version("1.12.2", "spigot-1.12.2-R0.1-SNAPSHOT-b1591.jar"),
			new Version("LATEST", "spigot-latest.jar")
	}),

	TACOSPIGOT("https://yivesmirror.com/files/tacospigot/%version%", new Version[] {
			new Version("1.8.8", "TacoSpigot-1.8.8.jar"),
			new Version("1.11.2", "TacoSpigot-1.11.2-b102.jar"),
			new Version("1.12.2", "TacoSpigot-1.12.2-b109.jar"),
			new Version("LATEST", "TacoSpigot-latest.jar")
	}),

	PAPERSPIGOT("https://yivesmirror.com/files/paperspigot/%version%", new Version[]{
			new Version("1.7.10", "PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar"),
			new Version("1.8", "PaperSpigot-1.8-R0.1-SNAPSHOT-b235.jar"),
			new Version("1.8.3", "PaperSpigot-1.8.3-R0.1-SNAPSHOT-latest.jar"),
			new Version("1.8.8", "PaperSpigot-1.8.8-R0.1-SNAPSHOT-latest.jar"),
			new Version("1.11.2", "PaperSpigot-1.11.2-b1104.jar"),
			new Version("1.12.2", "PaperSpigot-1.12.2-b1322.jar"),
			new Version("LATEST", "PaperSpigot-latest.jar")
	}),

	HOSE("https://yivesmirror.com/files/hose/%version%", new Version[]{
			new Version("1.8.8", "hose-1.8.8.jar"),
			new Version("1.9.4", "hose-1.9.4.jar"),
			new Version("1.10.2", "hose-1.10.2.jar"),
			new Version("1.11.2", "hose-1.11.2.jar")
	});

	@Getter
	private final String url;

	@Getter
	private final Version[] versions;

	public final Version getVersionByName(final String name) {
		return Arrays.stream(this.versions).filter(version -> version.toString().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public final String toString() {
		return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
	}
}
