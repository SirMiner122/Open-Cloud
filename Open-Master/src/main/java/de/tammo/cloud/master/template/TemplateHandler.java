/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.template;

import lombok.Getter;

import java.io.File;

public class TemplateHandler {

	@Getter
	private final File globalTemplate = new File("global");

	@Getter
	private final File proxyTemplate = new File("proxy");

}