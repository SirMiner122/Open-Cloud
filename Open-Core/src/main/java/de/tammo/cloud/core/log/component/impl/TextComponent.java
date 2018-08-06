/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.log.component.impl;

import de.tammo.cloud.core.log.*;
import de.tammo.cloud.core.log.component.LoggerComponent;
import de.tammo.cloud.core.log.event.NextLoggerComponentEvent;
import de.tammo.cloud.event.EventService;
import de.tammo.cloud.service.ServiceProvider;
import java.util.Date;
import lombok.RequiredArgsConstructor;

/**
 * Basic implementation of {@link LoggerComponent}
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class TextComponent implements LoggerComponent {

	/**
	 * Content which should print
	 *
	 * @since 1.0
	 */
	private final Object content;

	/**
	 * {@link LogLevel} of the log message
	 */
	private final LogLevel logLevel;

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		if(Logger.getContext().getLevel().getLevel() > this.logLevel.getLevel()) return;

		System.out.println(this.logLevel.getColor().getColorString() + "\r[" + Logger.getContext().getTimeFormat().format(new Date()) +
				"] " + Logger.getContext().getPrefix() + " [" + this.logLevel.getName() + "] " + this.content.toString() + ConsoleColor.RESET.getColorString());

		System.out.print("\r> ");

		ServiceProvider.getService(EventService.class).fireEvent(new NextLoggerComponentEvent());
	}

}
