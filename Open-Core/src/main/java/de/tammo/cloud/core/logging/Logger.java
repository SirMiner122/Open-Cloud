/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.logging;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class Logger {

	@Setter
	private static String prefix;

	@Setter
	private static LogLevel level = LogLevel.INFO;

	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public static void debug(final Object any) {
		log(any, LogLevel.DEBUG);
	}

	public static void info(final Object any) {
		log(any, LogLevel.INFO);
	}

	public static void warn(final Object any) {
		log(any, LogLevel.WARNING);
	}

	public static void error(final Object any, final Exception exception) {
		log(any, LogLevel.ERROR);
		exception.printStackTrace();
	}

	private static void log(final Object any, final LogLevel logLevel) {
		if (level.getLevel() > logLevel.getLevel()) return;

		System.out.println("\r[" + timeFormat.format(new Date()) + "] " + prefix + " [" + logLevel.getName() + "] " + any.toString());
		System.out.print("\r> ");
	}

	public static void progress(final long current, final long length) {
		final int percent = (int) (((double) current / length) * 100);
		System.out.print("\r[" + timeFormat.format(new Date()) + "] " + prefix + " [" + LogLevel.INFO.getName() + "] " + createProgress(percent, current, length));

		if (percent == 100) {
			System.out.println();
			System.out.print("\r>");
		}
	}

	private static String createProgress(final int percent, final long current, final long length) {
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
