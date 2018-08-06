/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.servergroup;

import de.tammo.cloud.service.Service;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ServerGroupService implements Service {

	@Setter
	@Getter
	private ArrayList<ServerGroup> serverGroups = new ArrayList<>();

	public final ServerGroup getServerGroupByName(final String name) {
		return this.serverGroups.stream().filter(serverGroup -> serverGroup.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	public void addServerGroup(final ServerGroup serverGroup) {
		this.serverGroups.add(serverGroup);
	}

	public void removeServerGroup(final ServerGroup serverGroup) {
		this.serverGroups.remove(serverGroup);
	}

}