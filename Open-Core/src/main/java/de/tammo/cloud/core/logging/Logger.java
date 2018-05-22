/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.logging;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class Logger {

	private final String logPath;

	private final String prefix;

	private final LogLevel level;

	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy//MM//dd");

	public void debug(final Object any) {
		this.log(any, LogLevel.DEBUG);
	}

	public void info(final Object any) {
		this.log(any, LogLevel.INFO);
	}

	public void warn(final Object any) {
		this.log(any, LogLevel.WARNING);
	}

	public void error(final Object any, final Exception exception) {
		this.log(any, LogLevel.ERROR);
		exception.printStackTrace();
	}

	private void log(final Object any, final LogLevel logLevel) {
		if (this.level.getLevel() > logLevel.getLevel()) return;

		System.out.println("\r[" + this.timeFormat.format(new Date()) + "] " + this.prefix + " [" + logLevel.getName() + "] " + any.toString());
		System.out.print("\r> ");
	}

	public void progress(final long current, final long length) {
		final int percent = (int) (((double) current / length) * 100);
		System.out.print("\r[" + this.timeFormat.format(new Date()) + "] " + this.prefix + " [" + LogLevel.INFO
				.getName() + "] " + this.createProgress(percent, current, length));

		if (percent == 100) {
			System.out.println();
			System.out.print("\r>");
		}
	}

	private String createProgress(final int percent, final long current, final long length) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < 25; i++) {
			if (i < percent / 4) {
				stringBuilder.append("=");
			} else if(i == percent / 4) {
				stringBuilder.append(">");
			} else {
				stringBuilder.append(" ");
			}
		}
		stringBuilder.append("]  ").append(current / 1024).append("/").append(length / 1024).append
				(" KB");
		return stringBuilder.toString();
	}

}
