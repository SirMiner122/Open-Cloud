/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.setup;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.StringRequest;
import de.tammo.cloud.master.Master;
import de.tammo.cloud.security.Hashing;
import de.tammo.cloud.security.user.CloudUser;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.UUID;

public class LoginSetup implements Setup {

	public void setup(final ConsoleReader reader) throws IOException {
		if (Master.getMaster().getCloudUserHandler().getCloudUsers().isEmpty()) {
			Logger.info("There is currently no user created. Creating the first user -> ");
			new StringRequest().request("Please enter a name for the setup user:", reader, name -> {
				if (name.equalsIgnoreCase("exit")) {
					Logger.info("\"exit\" is an invalid username. Exiting...");
					Master.getMaster().shutdown();
				} else {
					try {
						new StringRequest().request("Please enter the password for the setup user:", reader, input -> Master.getMaster().getCloudUserHandler().getCloudUsers().add(new CloudUser(name, UUID.randomUUID(), Hashing.hash(input))));
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
		new StringRequest().request("Please enter your username:", reader, name -> {
			if (name.equalsIgnoreCase("exit")) {
				Master.getMaster().shutdown();
			} else {
				final CloudUser cloudUser = Master.getMaster().getCloudUserHandler().findCloudUserByName(name);
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
