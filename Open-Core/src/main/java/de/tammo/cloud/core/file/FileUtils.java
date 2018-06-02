/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Objects;

public class FileUtils {

	public static void deleteDir(final File dir) throws IOException {
		if (!Files.exists(dir.toPath())) throw new FileNotFoundException();

		for (final File file : Objects.requireNonNull(dir.listFiles())) {
			if (file.isDirectory()) {
				deleteDir(file);
			} else {
				Files.delete(file.toPath());
			}
		}

		Files.delete(dir.toPath());
	}

	public static void zip(final File dir, final String zipPath) throws ZipException {
		final ZipFile zipFile = new ZipFile(zipPath + ".zip");
		final ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(file -> {
			try {
				if (file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				} else {
					zipFile.addFile(file, parameters);
				}
			} catch (ZipException e) {
				e.printStackTrace();
			}
		});
	}

	public static void unzip(final String zipPath, final String targetDir) throws ZipException {
		new ZipFile(zipPath).extractAll(targetDir);
	}

}