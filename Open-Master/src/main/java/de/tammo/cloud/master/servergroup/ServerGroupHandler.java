/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.servergroup;

import lombok.Getter;

import java.util.ArrayList;

public class ServerGroupHandler {

	@Getter
	private final ArrayList<ServerGroup> serverGroups = new ArrayList<>();

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