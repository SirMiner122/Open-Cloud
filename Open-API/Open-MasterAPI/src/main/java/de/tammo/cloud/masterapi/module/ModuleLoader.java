/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */
package de.tammo.cloud.masterapi.module;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Loading {@link Module}s with an {@link URLClassLoader}
 *
 * @author x7Airworker, Tammo0987
 * @version 1.0
 * @since 1.0
 */
public class ModuleLoader {

    /**
     * Load {@link Module}s from one directory
     *
     * @param dir Folder would be loaded from this directory
     *
     * @since 1.0
     */
    public void loadModulesFromDirectory(final File dir) {
        if(dir == null || dir.isFile()) return;

        final List<File> moduleFiles = Arrays
                .stream(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> file.getName().endsWith(".jar"))
                .collect(Collectors.toList());

        final URLClassLoader loader = new URLClassLoader((URL[]) moduleFiles
                .stream()
                .map(file -> {
                    try {
                        return file.toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .toArray());

        moduleFiles
                .stream()
                .map(file -> {
                    try {
                        return new JarFile(file).entries();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(entry -> {
                    while (entry.hasMoreElements()) {
                        final JarEntry jarEntry = entry.nextElement();
                        if (!jarEntry.getName().endsWith(".class")) continue;

                        try {
                            final Class<?> entryClass = loader.loadClass(jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().length() - 6));
                            if (Module.class.isAssignableFrom(entryClass) && entryClass.isAnnotationPresent(Module.Info.class)) {
                                final Module module = (Module) entryClass.newInstance();
                            }
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
