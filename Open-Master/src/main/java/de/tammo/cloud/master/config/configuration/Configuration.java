/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.config.configuration;

import lombok.Data;

@Data
public class Configuration {

	private int webPort = 80;

	private int nettyPort = 1337;

}
