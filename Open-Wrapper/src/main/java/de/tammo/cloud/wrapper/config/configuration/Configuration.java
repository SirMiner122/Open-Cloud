/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.wrapper.config.configuration;

import lombok.Data;

@Data
public class Configuration {

	private String masterHost = "127.0.0.1";

	private String key = "";

	private int masterPort = 1337;

	private int webPort = 80;

	private int averageSeconds = 5;

}
