/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.command;

import java.lang.annotation.*;

/**
 * Interface to implement commands
 *
 * @author Tammo
 * @version 2.0
 * @since 1.0
 */
public interface Command {

	/**
	 * @param args Arguments from the command line message to execute the command with parameters
	 *
	 * @return {@code true} if the {@link Command} was successfully executed
	 *
	 * @since 1.0
	 */
	boolean execute(final String[] args);

	/**
	 * @return {@link Info} about the {@link Command}
	 *
	 * @since 2.0
	 */
	default Info getInfo() {
		return this.getClass().getAnnotation(Info.class);
	}

	/**
	 * Annotation to declare the {@link Command}
	 *
	 * @since 1.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface Info {

		/**
		 * @return List of all possible trigger
		 *
		 * @since 1.0
		 */
		String[] names();

		/**
		 * @return Description from the {@link Command}
		 *
		 * @since 2.0
		 */
		String description() default "";
	}

}
