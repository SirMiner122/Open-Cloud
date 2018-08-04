/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.masterapi.module;

public interface Module {

	default void onLoad() {}

	default void onEnable() {}

	default void onDisable() {}

	default void onStop() {}

	default String getName() {

		return "";
	}

	default String getVersion() {

		return "1.0-SNAPSHOT";
	}

	default String getAuthor() {

		return "";
	}

}
