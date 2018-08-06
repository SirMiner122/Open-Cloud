package de.tammo.cloud.core.log.component.impl;

import de.tammo.cloud.core.log.LogLevel;
import de.tammo.cloud.core.log.Logger;
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

		System.out.println("\r[" + Logger.getContext().getTimeFormat().format(new Date()) + "] "
				+ Logger.getContext().getPrefix() + " [" + this.logLevel.getName() + "] " + this.content.toString());
		System.out.print("\r> ");

		ServiceProvider.getService(EventService.class).fireEvent(new NextLoggerComponentEvent());
	}

}
