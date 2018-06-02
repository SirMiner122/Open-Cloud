/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.web.handler;

import de.tammo.cloud.web.WebServer;
import de.tammo.cloud.web.mapping.Mapping;
import de.tammo.cloud.web.response.HttpResponseBuilder;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import lombok.RequiredArgsConstructor;

import java.net.*;

@RequiredArgsConstructor
public class WebServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final WebRequestHandlerProvider webRequestHandlerProvider;

	protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest httpRequest) {
		final URI uri = URI.create(httpRequest.uri());
		String path = uri.getRawPath();
		if (path == null) {
			path = "/";
		}

		if (path.endsWith("/") && path.length() > 1) {
			path = path.substring(0, path.length() - 1);
		}

		FullHttpResponse response = null;

		for (final RequestHandler requestHandler : this.webRequestHandlerProvider.getRequestHandlers()) {
			if (!requestHandler.getClass().getAnnotation(Mapping.class).path().equals(path.replace(WebServer.URL, ""))) {
				continue;
			}

			switch (httpRequest.method().name()) {

				case "GET":
					response = requestHandler.get(httpRequest);
					break;

				case "POST":
					response = requestHandler.post(httpRequest);
					break;

				case "DELETE":
					response = requestHandler.delete(httpRequest);
					break;

				default:

			}
		}

		if (response == null) {
			response = new HttpResponseBuilder(httpRequest, HttpResponseStatus.NOT_FOUND).getResponse();
		}

		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}