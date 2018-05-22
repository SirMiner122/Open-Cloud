/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.setup.version;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Version {

	@Getter
	private final String version;

	@Getter
	private final String url;

	public final String toString() {
		return this.version;
	}
}
