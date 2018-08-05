/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.service;

import java.util.ArrayList;

/**
 * Class to provide services to remove many instances from the the main classes, to decrease the size to improve the
 * overview
 *
 * @author Tammo
 * @since 1.0
 */
public class ServiceProvider {

	/**
	 * List which is holding all instances of services
	 */
	private static ArrayList<Service> services = new ArrayList<>();

	/**
	 * Add a service instance to the list
	 *
	 * @param service Service which should added to the list
	 */
	public static void addService(final Service service) {
		services.add(service);
	}

	/**
	 * Initialised all services
	 */
	public static void init() {
		services.forEach(Service::init);
	}

	/**
	 * Provide a specific service
	 *
	 * @param serviceClass Class of the service, which is wanted
	 * @param <T> Type of the Service to cast it automatically to the wanted service
	 * @return The service instance filtered by {@param serviceClass}
	 */
	public static <T extends Service> T getService(final Class<T> serviceClass) {
		return serviceClass.cast(services.stream().filter(s -> s.getClass() == serviceClass).findFirst().get());
	}

}
