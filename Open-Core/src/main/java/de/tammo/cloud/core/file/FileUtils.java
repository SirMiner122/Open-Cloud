/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class is for util methods, to simplify the use with files
 *
 * @author Tammo
 * @since  1.0
 */
public class FileUtils {

	/**
	 * Deletes a directory recursively
	 *
	 * @param dir Directory, which should deleted
	 * @throws IOException The deletion of the directory has failed
	 */
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

	/**
	 * Put a directory into an zip archive
	 *
	 * @param dir Directory which should put in the archive
	 * @param zipPath Path to the zip file
	 * @throws ZipException The packaging of the zip archive has failed
	 */
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

	/**
	 * Unzip a directory to a specific path
	 *
	 * @param zipPath Path, where the zip archive is located
	 * @param targetDir Target directory, where the unzipped files should be located
	 * @throws ZipException The unzipping of the zip archive has failed
	 */
	public static void unzip(final String zipPath, final String targetDir) throws ZipException {
		new ZipFile(zipPath).extractAll(targetDir);
	}

}