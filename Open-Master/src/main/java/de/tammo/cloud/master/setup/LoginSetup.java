/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.setup;

import de.tammo.cloud.core.log.Logger;
import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.impl.StringRequest;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.security.Hashing;
import de.tammo.cloud.security.user.CloudUser;
import de.tammo.cloud.security.user.CloudUserService;
import de.tammo.cloud.service.ServiceProvider;
import java.io.IOException;
import java.util.UUID;
import jline.console.ConsoleReader;

public class LoginSetup implements Setup {

	public void setup(final ConsoleReader reader) throws IOException {
		if (ServiceProvider.getService(CloudUserService.class).getCloudUsers().isEmpty()) {
			Logger.info("There is currently no user created. Creating the first user -> ");
			new StringRequest("Please enter a names for the setup user:", reader).request(name -> {
				if (name.equalsIgnoreCase("exit")) {
					Logger.info("\"exit\" is an invalid username. Exiting...");
					Master.getMaster().shutdown();
				} else {
					try {
						new StringRequest("Please enter the password for the setup user:", reader).request(input -> ServiceProvider.getService(CloudUserService.class).getCloudUsers().add(new CloudUser(name, UUID.randomUUID(), Hashing.hash(input))));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			this.login(reader);
		}
	}

	private void login(final ConsoleReader reader) throws IOException {
		Logger.info("Welcome to the login!");
		new StringRequest("Please enter your username:", reader).request(name -> {
			if (name.equalsIgnoreCase("exit")) {
				Master.getMaster().shutdown();
			} else {
				final CloudUser cloudUser = ServiceProvider.getService(CloudUserService.class).findCloudUserByName(name);
				if (cloudUser == null) {
					try {
						this.login(reader);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Logger.info("Password:");
					try {
						final String password = reader.readLine('*');
						if (Hashing.verify(String.valueOf(password), cloudUser.getHash())) {
							Logger.info("You successfully logged in!");
						} else {
							try {
								this.login(reader);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
	}

}
