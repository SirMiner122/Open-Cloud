/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.core.log;

import de.tammo.cloud.core.log.component.LoggerComponent;
import de.tammo.cloud.core.log.component.impl.ErrorComponent;
import de.tammo.cloud.core.log.component.impl.TextComponent;
import de.tammo.cloud.core.log.event.NextLoggerComponentEvent;
import de.tammo.cloud.core.queue.Queue;
import de.tammo.cloud.event.EventService;
import de.tammo.cloud.event.EventTarget;
import de.tammo.cloud.service.ServiceProvider;
import lombok.*;

/**
 * Class to log formatted content to the console
 *
 * @author Tammo
 * @since 1.0
 */
@RequiredArgsConstructor
public class Logger {

	/**
	 * Have a look at {@link LoggerContext}
	 */
	@Setter
	@Getter
	private static LoggerContext context;

	/**
	 * Queue of log messages to keep the right sequence
	 */
	@Getter
	private static Queue<LoggerComponent> queue = new Queue<>();

	@EventTarget
	public void onNext(final NextLoggerComponentEvent event) {
		if (!queue.isEmpty()) {
			final LoggerComponent component = queue.getFirst();
			component.print();
		}
	}

	/**
	 * Printing a message with the level DEBUG
	 *
	 * @param content Content, which should be log
	 */
	public static void debug(final Object content) {
		log(content, LogLevel.DEBUG);
	}

	/**
	 * Printing a message with the level INFO
	 *
	 * @param content Content, which should be log
	 */
	public static void info(final Object content) {
		log(content, LogLevel.INFO);
	}

	/**
	 * Printing a message with the level WARNING
	 *
	 * @param content Content, which should be log
	 */
	public static void warn(final Object content) {
		log(content, LogLevel.WARNING);
	}

	/**
	 * Printing a message with the level WARNING
	 *
	 * @param content Content, which should be log
	 * @param exception The exception which was thrown. The stacktrace of the exception will be printed
	 */
	public static void error(final Object content, final Exception exception) {
		queue.offer(new ErrorComponent(content, exception));
		ServiceProvider.getService(EventService.class).fireEvent(new NextLoggerComponentEvent());
	}

	/**
	 * Printing a message with the level {@param logLevel}
	 *
	 * @param content Content, which should be log
	 * @param logLevel Logging level of the content
	 */
	private static void log(final Object content, final LogLevel logLevel) {
		queue.offer(new TextComponent(content, logLevel));
		ServiceProvider.getService(EventService.class).fireEvent(new NextLoggerComponentEvent());
	}

}
