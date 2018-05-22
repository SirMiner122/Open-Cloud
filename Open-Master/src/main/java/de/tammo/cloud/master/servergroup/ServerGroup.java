/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.servergroup;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerGroup {

	private final String name;

	private int minServer;

	private int maxServer;

	private int minRam;

	private int maxRam;

}
