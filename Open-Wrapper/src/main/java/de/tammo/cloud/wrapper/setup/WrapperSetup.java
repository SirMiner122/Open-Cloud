/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.setup;

import de.tammo.cloud.core.setup.Setup;
import de.tammo.cloud.core.setup.requests.StringRequest;
import de.tammo.cloud.wrapper.Wrapper;
import de.tammo.cloud.wrapper.network.packets.out.WrapperKeyOutPacket;
import jline.console.ConsoleReader;

import java.io.IOException;

public class WrapperSetup implements Setup {

	public void setup(final ConsoleReader reader) throws IOException {
		if (Wrapper.getWrapper().getConfiguration().getKey().isEmpty()) {
			new StringRequest().request("Type in the key, you received from the Open-Master", reader, key -> Wrapper.getWrapper().setKey(key));
			Wrapper.getWrapper().getNetworkHandler().sendPacketToMaster(new WrapperKeyOutPacket(Wrapper.getWrapper().getKey()));
		} else {
			Wrapper.getWrapper().setKey(Wrapper.getWrapper().getConfiguration().getKey());
			Wrapper.getWrapper().getNetworkHandler().sendPacketToMaster(new WrapperKeyOutPacket(Wrapper.getWrapper().getConfiguration().getKey()));
		}
	}

}
