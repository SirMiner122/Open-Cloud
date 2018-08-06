/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.web.validation;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.tammo.cloud.security.Hashing;
import de.tammo.cloud.security.user.CloudUser;
import de.tammo.cloud.security.user.CloudUserService;
import de.tammo.cloud.service.ServiceProvider;
import de.tammo.cloud.web.handler.RequestHandler;
import de.tammo.cloud.web.mapping.Mapping;
import de.tammo.cloud.web.response.HttpResponseBuilder;
import io.netty.handler.codec.http.*;

@Mapping(path = "/validation")
public class Validation implements RequestHandler {

	public final FullHttpResponse post(final FullHttpRequest request) {
		if (request.headers().contains("account-names") && request.headers().contains("account-password") && this.validateAccount(request.headers().get("account-names"), request.headers().get("account-password"))) {
			return this.buildSuccess(request);
		} else if (request.headers().contains("account-token") && this.validateAccount(request.headers().get("account-token"))) {

		} else {
			return new HttpResponseBuilder(request, HttpResponseStatus.BAD_REQUEST).getResponse();
		}

		return null;
	}

	private boolean validateAccount(final String name, final String password) {
		final CloudUser cloudUser = ServiceProvider.getService(CloudUserService.class).findCloudUserByName(name);
		if (cloudUser == null) {
			return false;
		}

		return Hashing.verify(password, cloudUser.getHash());
	}

	private boolean validateAccount(final String token) {
		return false;
	}

	private FullHttpResponse buildSuccess(final FullHttpRequest request) {
		final FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
		final JsonObject object = new JsonObject();
		object.addProperty("success", true);

		response.content().writeBytes(new GsonBuilder().setPrettyPrinting().create().toJson(object).getBytes());
		return response;
	}

}
