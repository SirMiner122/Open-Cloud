/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.proxy;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Proxy extends Plugin {

	private final UUID uuid = UUID.randomUUID();

	public void onEnable() {
		//TODO generate id here and connect to master
	}

	public void onDisable() {

	}

	private void addServer(final String name, final String host, final int port, final String motd) {
		final ServerInfo serverInfo = this.getProxy().constructServerInfo(name, new InetSocketAddress(host, port), motd,false);
		this.getProxy().getServers().put(name, serverInfo);
	}

}
