/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.setup;

import de.tammo.cloud.core.service.ServiceProvider;
import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.impl.StringRequest;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.network.NetworkProviderService;
import de.tammo.cloud.wrapper.network.packets.out.WrapperKeyOutPacket;
import jline.console.ConsoleReader;

import java.io.IOException;

public class WrapperSetup implements Setup {

	public void setup(final ConsoleReader reader) throws IOException {
		if (Wrapper.getWrapper().getConfiguration().getKey().isEmpty()) {
			new StringRequest("Type in the key, you received from the Open-Master", reader).request(key -> Wrapper.getWrapper().setKey(key));
			ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new WrapperKeyOutPacket(Wrapper.getWrapper().getKey()));
		} else {
			Wrapper.getWrapper().setKey(Wrapper.getWrapper().getConfiguration().getKey());
			ServiceProvider.getService(NetworkProviderService.class).sendPacketToMaster(new WrapperKeyOutPacket(Wrapper.getWrapper().getConfiguration().getKey()));
		}
	}

}
