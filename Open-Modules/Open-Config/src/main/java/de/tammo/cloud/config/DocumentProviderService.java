/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.config;

import com.google.common.reflect.ClassPath;
import de.tammo.cloud.core.service.Service;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

public class DocumentProviderService implements Service {

	@Getter
	private final ArrayList<DocumentFile> files = new ArrayList<>();

	public DocumentProviderService(final String path) {
		try {
			for (final ClassPath.ClassInfo classInfo : ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(path)) {
				final Class fileClass = Class.forName(classInfo.getName());
				if (fileClass.getSuperclass() == DocumentFile.class) {
					this.files.add((DocumentFile) fileClass.newInstance());
				}
			}
		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		this.loadFiles();
	}

	private void loadFiles() {
		this.files.forEach(documentFile -> {
			try {
				documentFile.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void saveFiles() {
		this.files.forEach(documentFile -> {
			try {
				documentFile.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
