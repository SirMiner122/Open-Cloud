/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.core.service;

import java.util.ArrayList;

/**
 * Class to provide services to remove many instances from the the main classes, to decrease the size to improve the
 * overview
 *
 * @author Tammo
 * @version 1.0
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
