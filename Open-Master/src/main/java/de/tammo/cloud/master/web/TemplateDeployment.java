/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */

package de.tammo.cloud.master.web;

import de.tammo.cloud.core.file.FileUtils;
import de.tammo.cloud.web.handler.RequestHandler;
import de.tammo.cloud.web.mapping.Mapping;
import de.tammo.cloud.web.response.HttpResponseBuilder;
import io.netty.handler.codec.http.*;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mapping(path = "/deployment")
public class TemplateDeployment implements RequestHandler {

	public final FullHttpResponse get(final FullHttpRequest request) {
		if (!request.headers().contains("type")) {
			return new HttpResponseBuilder(request, HttpResponseStatus.PRECONDITION_REQUIRED).getResponse();
		}

		final String type = request.headers().get("type");

		final File temp = new File("temp//");

		if (Files.notExists(temp.toPath())) {
			try {
				Files.createDirectories(temp.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FullHttpResponse response = new HttpResponseBuilder(request, HttpResponseStatus.OK).getResponse();

		switch (type.toLowerCase()) {

			case "global":
				final File globalDir = new File("global//");

				if (!globalDir.exists()) {
					return new HttpResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
				}

				try {
					FileUtils.zip(globalDir, "temp//global");
				} catch (ZipException e) {
					e.printStackTrace();
				}

				final File globalZip = new File("temp//global.zip");
				if (Files.notExists(globalZip.toPath())) {
					return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();
				}

				try {
					response.content().writeBytes(Files.readAllBytes(globalZip.toPath()));
					return response;
				} catch (IOException e) {
					e.printStackTrace();
				}

				return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();

			case "proxy":
				final File proxyDir = new File("proxy//");

				if (!proxyDir.exists()) {
					return new HttpResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
				}

				try {
					FileUtils.zip(proxyDir, "temp//proxy");
				} catch (ZipException e) {
					e.printStackTrace();
				}

				final File proxyZip = new File("temp//proxy.zip");
				if (Files.notExists(proxyZip.toPath())) {
					return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();
				}

				try {
					response.content().writeBytes(Files.readAllBytes(proxyZip.toPath()));
					return response;
				} catch (IOException e) {
					e.printStackTrace();
				}

				return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();

			case "template":
				if (!request.headers().contains("group")) {
					return new HttpResponseBuilder(request, HttpResponseStatus.PRECONDITION_REQUIRED).getResponse();
				}

				final String group = request.headers().get("group");

				final File templateDir = new File("template//" + group);

				if (!templateDir.exists()) {
					return new HttpResponseBuilder(request, HttpResponseStatus.NOT_FOUND).getResponse();
				}

				try {
					FileUtils.zip(templateDir, "temp//" + group);
				} catch (ZipException e) {
					e.printStackTrace();
				}

				final File zip = new File("temp//" + group + ".zip");
				if (Files.notExists(zip.toPath())) {
					return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();
				}

				try {
					response.content().writeBytes(Files.readAllBytes(zip.toPath()));
					return response;
				} catch (IOException e) {
					e.printStackTrace();
				}

				return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();

			default:
				return new HttpResponseBuilder(request, HttpResponseStatus.INTERNAL_SERVER_ERROR).getResponse();
		}
	}

}
