package de.tammo.cloud.core.log;

import lombok.Value;

import java.text.SimpleDateFormat;

/**
 * This class is be used for init a {@link Logger} with a context
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
@Value
public class LoggerContext {

	/**
	 * Prefix for the messages from the logger
	 *
	 * @since 1.0
	 */
	private final String prefix;

	/**
	 * Level of the {@link Logger} to get only the wanted information
	 */
	private final LogLevel level;

	/**
	 * {@link SimpleDateFormat} to parse the current time
	 *
	 * @since 1.0
	 */
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

}