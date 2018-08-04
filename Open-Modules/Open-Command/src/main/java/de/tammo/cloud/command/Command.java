/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.command;

import java.lang.annotation.*;

public interface Command {

	boolean execute(final String[] args);

	default void printHelp() {}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface CommandInfo {

		String name();

		String[] aliases() default {};
	}

}
