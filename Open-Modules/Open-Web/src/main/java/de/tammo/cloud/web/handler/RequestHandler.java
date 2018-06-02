/*
 * Copyright (c) 2018. File created by Tammo
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
