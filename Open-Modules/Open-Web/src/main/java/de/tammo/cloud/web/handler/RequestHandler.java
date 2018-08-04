/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.web.handler;

import io.netty.handler.codec.http.*;

public interface RequestHandler {

	default FullHttpResponse get(final FullHttpRequest request) {
		return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.METHOD_NOT_ALLOWED);
	}

	default FullHttpResponse post(final FullHttpRequest request) {
		return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.METHOD_NOT_ALLOWED);
	}

	default FullHttpResponse delete(final FullHttpRequest request) {
		return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.METHOD_NOT_ALLOWED);
	}

}
