/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.logging;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to log formatted content to the console
 *
 * @author Tammo
 * @since 1.0
 */
@RequiredArgsConstructor
public class Logger {

	/**
	 * Prefix for the formatting
	 */
	@Setter
	private static String prefix;

	/**
	 * Current level to log. Default level is INFO. This could be changed to DEBUG, to see the debug messages
	 */
	@Setter
	private static LogLevel level = LogLevel.INFO;

	/**
	 * {@link SimpleDateFormat} to parse the current time
	 */
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Printing a message with the level DEBUG
	 *
	 * @param any Content, which should be log
	 */
	public static void debug(final Object any) {
		log(any, LogLevel.DEBUG);
	}

	/**
	 * Printing a message with the level INFO
	 *
	 * @param any Content, which should be log
	 */
	public static void info(final Object any) {
		log(any, LogLevel.INFO);
	}

	/**
	 * Printing a message with the level WARNING
	 *
	 * @param any Content, which should be log
	 */
	public static void warn(final Object any) {
		log(any, LogLevel.WARNING);
	}

	/**
	 * Printing a message with the level WARNING
	 *
	 * @param any Content, which should be log
	 * @param exception The exception which was thrown. The stacktrace of the exception will be printed
	 */
	public static void error(final Object any, final Exception exception) {
		log(any, LogLevel.ERROR);
		exception.printStackTrace();
	}

	/**
	 * Printing a message with the level {@param logLevel}
	 *
	 * @param any Content, which should be log
	 * @param logLevel Logging level of the content
	 */
	private static void log(final Object any, final LogLevel logLevel) {
		if (level.getLevel() > logLevel.getLevel()) return;

		System.out.println("\r[" + timeFormat.format(new Date()) + "] " + prefix + " [" + logLevel.getName() + "] " + any.toString());
		System.out.print("\r> ");
	}

	/**
	 * Printing a download progress to the console
	 *
	 * @param current Current downloaded size
	 * @param length Length of the content to download
	 */
	public static void progress(final long current, final long length) {
		final int percent = (int) (((double) current / length) * 100);
		System.out.print("\r[" + timeFormat.format(new Date()) + "] " + prefix + " [" + LogLevel.INFO.getName() + "] " + createProgress(current, length));

		if (percent == 100) {
			System.out.println();
			System.out.print("\r>");
		}
	}

	/**
	 * Create a progress bar for the current download
	 *
	 * @param current Current downloaded size
	 * @param length Length of the content to download
	 * @return String which show the current progress of the download in a progress bar
	 */
	private static String createProgress(final long current, final long length) {
		final int percent = (int) (((double) current / length) * 100);
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < 25; i++) {
			if (i < percent / 4) {
				stringBuilder.append("=");
			} else if (i == percent / 4) {
				stringBuilder.append(">");
			} else {
				stringBuilder.append(" ");
			}
		}
		stringBuilder.append("]  ").append(current / 1024).append("/").append(length / 1024).append(" KB");
		return stringBuilder.toString();
	}

}
