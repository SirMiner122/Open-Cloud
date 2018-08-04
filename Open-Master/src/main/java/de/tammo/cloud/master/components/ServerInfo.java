/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
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
