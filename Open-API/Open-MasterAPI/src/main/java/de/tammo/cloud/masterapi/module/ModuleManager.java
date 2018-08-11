/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */
package de.tammo.cloud.masterapi.module;

import java.util.*;

/**
 * The core of the {@link Module} system to manage the {@link Module}s
 *
 * @author x7Airworker, Tammo
 * @version 1.0
 * @since 1.0
 */
public class ModuleManager {

    /**
     * {@link Map} to store {@link Module}s with the name as key
     *
     * @since 1.0
     */
    private Map<String, Module> modules = new HashMap<>();

    /**
     * Register {@link Module}s in the {@link ModuleManager}
     *
     * @param modules {@link Module}s to register
     *
     * @since 1.0
     */
    public void registerModules(final Module... modules) {
        Arrays.asList(modules).forEach(this::registerModule);
    }

    /**
     * Register on {@link Module} in the {@link ModuleManager}
     *
     * @param module {@link Module} to register
     *
     * @since 1.0
     */
    public void registerModule(final Module module) {
        this.modules.put(module.getInfo().name(), module);
        module.onEnable();
    }

    /**
     * Unregister all registered {@link Module}s
     *
     * @since 1.0
     */
    public void unregisterAllModules() {
        this.unregisterModules((Module[]) this.modules.values().toArray());
    }

    /**
     * Unregister {@link Module}s from the {@link ModuleManager}
     *
     * @param modules {@link Module}s to unregister
     *
     * @since 1.0
     */
    public void unregisterModules(final Module... modules) {
        Arrays.asList(modules).forEach(this::unregisterModule);
    }

    /**
     * Unregister {@link Module} from {@link ModuleManager}
     *
     * @param module {@link Module} to unregister
     *
     * @since 1.0
     */
    public void unregisterModule(final Module module) {
        this.modules.remove(module.getInfo().name());
        module.onDisable();
    }

    /**
     * @return {@link Module} by name
     *
     * @param name Name of the wanted {@link Module}
     *
     * @since 1.0
     */
    public final Module getModuleByName(final String name) {
        return this.modules.get(name);
    }

}
