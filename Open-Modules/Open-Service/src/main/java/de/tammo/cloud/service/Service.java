/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.service;

/**
 * Interface which is implemented, to add a class to the {@link ServiceProvider} to get
 * them as a {@link Service}
 */
public interface Service {

	/**
	 * Optional initialise method to init the {@link Service}.
	 * This method should be called at the beginning
	 */
	default void init() {}

}
