/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.security.user;

import de.tammo.cloud.core.service.Service;
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
