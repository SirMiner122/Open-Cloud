/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class DocumentFile {

	protected final File file;

	public DocumentFile(final File file) {
		this.file = file;

		if (Files.notExists(this.file.getParentFile().toPath())) {
			try {
				Files.createDirectories(this.file.getParentFile().toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (Files.notExists(this.file.toPath())) {
			try {
				this.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract void load() throws IOException;

	protected abstract void save() throws IOException;

}
