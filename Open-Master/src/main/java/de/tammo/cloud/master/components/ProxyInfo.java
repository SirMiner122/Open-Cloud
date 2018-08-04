/*
 * Copyright (c) 2018. File created by Tammo
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
