/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.components;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Builder
public class ProxyInfo {

	private final UUID uuid;

	private final ArrayList<ServerInfo> gameServerData;

}
