/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.security.user;

import de.tammo.cloud.service.Service;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class CloudUserService implements Service {

	private ArrayList<CloudUser> cloudUsers = new ArrayList<>();

	public final CloudUser findCloudUserByName(final String name) {
		return this.cloudUsers.stream().filter(cloudUser -> cloudUser.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}
