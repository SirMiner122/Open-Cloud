/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.setup.version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum  ProxyVersion {

	BUNGEECORD("https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar"),
	WATERFALL("https://destroystokyo.com/ci/job/Waterfall/lastSuccessfulBuild/artifact/Waterfall-Proxy/bootstrap/target/Waterfall.jar"),
	HEXACORD("https://yivesmirror.com/files/hexacord/HexaCord-v190.jar"),
	TRAVERTINE("https://yivesmirror.com/files/travertine/Travertine-latest.jar");

	@Getter
	private final String url;

	public final String toString() {
		return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
	}
}