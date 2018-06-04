/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.master.web.rest;

import de.tammo.cloud.master.Master;
import de.tammo.cloud.web.WebServer;
import de.tammo.cloud.web.handler.RequestHandler;
import de.tammo.cloud.web.mapping.Mapping;
import de.tammo.cloud.web.response.HttpResponseBuilder;
import io.netty.handler.codec.http.*;

@Mapping(path = "/wrappers")
public class WrapperInfo implements RequestHandler {

	public final FullHttpResponse get(final FullHttpRequest request) {
		final String wrappers = WebServer.GSON.toJson(Master.getMaster().getNetworkHandler().getWrappers());
		final FullHttpResponse response = new HttpResponseBuilder(request, HttpResponseStatus.OK).getResponse();
		response.headers().set("Content-Type", "application/json");
		response.content().writeBytes(wrappers.getBytes());
		return response;
	}
}
