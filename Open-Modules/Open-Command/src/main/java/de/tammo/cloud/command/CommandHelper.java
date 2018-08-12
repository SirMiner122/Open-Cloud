/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.command;

import de.tammo.cloud.core.log.Logger;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
public class CommandHelper {

	@Getter
	private final String mainCommand;

	private List<String> helpList = new ArrayList<>();

	public void addToHelpList(final String help) {
		this.helpList.add(help);
	}

	void printHelp() {
		Logger.info("<-- Command Help -->");
		Logger.info(this.mainCommand);
		this.helpList.forEach(Logger::info);
		Logger.info("");
	}
}

