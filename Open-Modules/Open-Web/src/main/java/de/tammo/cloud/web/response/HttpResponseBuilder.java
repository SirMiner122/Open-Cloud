/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.web.response;

import io.netty.handler.codec.http.*;
import lombok.Getter;

public class HttpResponseBuilder {

	@Getter
	private final FullHttpResponse response;

	public HttpResponseBuilder(final FullHttpRequest request, final HttpResponseStatus status) {
		this.response = new DefaultFullHttpResponse(request.protocolVersion(), status);
	}

	public final HttpResponseBuilder setHeader(final String key, final String value) {
		this.response.headers().set(key, value);
		return this;
	}

}
