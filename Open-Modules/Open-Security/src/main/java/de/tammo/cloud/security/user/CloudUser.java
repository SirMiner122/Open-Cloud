/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.security.user;

import lombok.Data;

import java.util.UUID;

@Data
public class CloudUser {

	private final String name;

	private final UUID uuid;

	private final String hash;

}
