/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.web.handler;

import com.google.common.reflect.ClassPath;
import de.tammo.cloud.web.mapping.Mapping;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

public class WebRequestHandlerProvider {

	@Getter
	private final ArrayList<RequestHandler> requestHandlers = new ArrayList<>();

	public final WebRequestHandlerProvider add(final RequestHandler requestHandler) {
		if (!requestHandler.getClass().isAnnotationPresent(Mapping.class)) {
			throw new IllegalStateException(requestHandler.getClass().getSimpleName() + " is missing Mapping Annotation!");
		} else {
			this.requestHandlers.add(requestHandler);
		}
		return this;
	}

	public final WebRequestHandlerProvider addPackage(final String requestHandlerPackage) {
		try {
			for (final ClassPath.ClassInfo classInfo : ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(requestHandlerPackage)) {
				final Class requestHandlerClass = classInfo.load();
				if (!requestHandlerClass.isAnnotationPresent(Mapping.class)) {
					throw new IllegalStateException(classInfo.getSimpleName() + " is missing Mapping Annotation!");
				} else {
					this.requestHandlers.add((RequestHandler) requestHandlerClass.newInstance());
				}
			}
		} catch (IOException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		return this;
	}

}
