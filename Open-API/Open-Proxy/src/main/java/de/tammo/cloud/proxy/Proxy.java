/*
 * Copyright (c) 2018. File created by Tammo
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
