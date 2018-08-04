/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.masterapi;

import de.tammo.cloud.masterapi.event.EventHandler;
import lombok.Getter;

public class CloudMasterAPI {

	@Getter
	private final EventHandler eventHandler = new EventHandler();

}
