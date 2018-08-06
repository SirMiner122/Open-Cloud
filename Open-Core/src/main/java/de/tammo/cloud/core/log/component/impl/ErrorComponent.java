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
 * Implementation of {@link LoggerComponent} to print an error
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class ErrorComponent implements LoggerComponent {

	/**
	 * Occurred error
	 *
	 * @since 1.0
	 */
	private final Object error;

	/**
	 * Exception which was thrown
	 *
	 * @since 1.0
	 */
	private final Exception exception;

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		System.out.println("\r[" + Logger.getContext().getTimeFormat().format(new Date()) + "] "
				+ Logger.getContext().getPrefix() + " [" + LogLevel.ERROR.getName() + "] " + this.error.toString());

		this.exception.printStackTrace();

		System.out.print("\r> ");

		ServiceProvider.getService(EventService.class).fireEvent(new NextLoggerComponentEvent());
	}

}
