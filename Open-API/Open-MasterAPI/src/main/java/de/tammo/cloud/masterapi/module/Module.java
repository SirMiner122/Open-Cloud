/*
 * Copyright (c) 2018, Open-Cloud-Services and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository.
 */
package de.tammo.cloud.masterapi.module;

import de.tammo.cloud.core.log.Logger;
import java.lang.annotation.*;

/**
 * Create custom {@link Module}s for the Open-Master
 *
 * @author Tammo
 * @version 1.0
 * @since 1.0
 */
public interface Module {

    /**
     * Enables the {@link Module}
     *
     * @since 1.0
     */
    default void onEnable() {
        Logger.info(this.getInfo().name() + " " + this.getInfo().version() + " was enabled!");
    }

    /**
     * Disables thee {@link Module}
     *
     * @since 1.0
     */
    default void onDisable() {
        Logger.info(this.getInfo().name() + " " + this.getInfo().version() + " was disabled!");
    }

    /**
     * @return {@link Info} about this {@link Module}
     */
    default Info getInfo()
    {
        return getClass().getAnnotation(Info.class);
    }

    /**
     * {@link Info} annotation to get information about this {@link Module}
     *
     * @author Tammo
     * @version 1.0
     * @since 1.0
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	@interface Info {

        /**
         * @return Name of the {@link Module}
         *
         * @since 1.0
         */
        String name();

        /**
         * @return Version of the {@link Module}
         *
         * @since 1.0
         */
        double version() default 1.0;

        /**
         * @return List of authors of the {@link Module}
         *
         * @since 1.0
         */
        String[] authors();
    }
}
