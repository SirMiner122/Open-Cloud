/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.components;

import de.tammo.cloud.core.service.Service;
import lombok.Getter;

import java.util.*;

@Getter
public class ComponentsProviderService implements Service {

	private final ArrayList<ProxyInfo> proxyInfoList = new ArrayList<>();

	private final ArrayList<ServerInfo> serverInfoList = new ArrayList<>();

	public void addProxyInfo(final ProxyInfo proxyInfo) {
		this.proxyInfoList.add(proxyInfo);
	}

	public void removeProxyInfo(final ProxyInfo proxyInfo) {
		this.proxyInfoList.remove(proxyInfo);
	}

	public final ProxyInfo getProxyInfoByUniqueId(final UUID uuid) {
		return this.proxyInfoList.stream().filter(proxyInfo -> proxyInfo.getUuid().toString().equals(uuid.toString())).findFirst().orElse(null);
	}

	public final ProxyInfo getProxyInfoByUniqueId(final String uuid) {
		return this.proxyInfoList.stream().filter(proxyInfo -> proxyInfo.getUuid().toString().equals(uuid)).findFirst().orElse(null);
	}

	public void addServerInfo(final ServerInfo serverInfo) {
		this.serverInfoList.add(serverInfo);
	}

	public void removeServerInfo(final ServerInfo serverInfo) {
		this.serverInfoList.remove(serverInfo);
	}

	public final ServerInfo getServerInfoByUniqueId(final UUID uuid) {
		return this.serverInfoList.stream().filter(serverInfo -> serverInfo.getUuid().toString().equals(uuid.toString())).findFirst().orElse(null);
	}

	public final ServerInfo getServerInfoByUniqueId(final String uuid) {
		return this.serverInfoList.stream().filter(serverInfo -> serverInfo.getUuid().toString().equals(uuid)).findFirst().orElse(null);
	}

	public final List<ServerInfo> getServerInfoListByGroup(final String serverGroup) {
		final ArrayList<ServerInfo> serverGroupInfoList = new ArrayList<>();
		this.serverInfoList.stream().filter(serverInfo -> serverInfo.getGroup().getName().equalsIgnoreCase(serverGroup)).forEach(serverGroupInfoList::add);
		return serverGroupInfoList;
	}

}
