/*
 * Copyright (c) 2018. File created by Tammo
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
