/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.components;

import de.tammo.cloud.master.servergroup.ServerGroup;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ServerInfo {

	private final UUID uuid;

	private final ServerGroup group;

	private final int port;

}
