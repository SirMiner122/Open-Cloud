/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.wrapper.components;

import java.io.IOException;

public interface ServerComponent {

	void start() throws IOException;

	void stop();

}
