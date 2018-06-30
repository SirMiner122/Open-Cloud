/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.service;

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
