/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.network.utils;

import lombok.Data;

@Data
public class ConnectableAddress {

	private final String host;

	private final int port;

}
