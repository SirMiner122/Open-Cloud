package de.tammo.cloud.masterapi.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation placed above classes that extends Module.
 * Gives Information about the Module see {@link de.tammo.cloud.masterapi.module.Module}#getModuleInfo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo
{
    /**
     * The Plugins name
     */
    String name();

    /**
     * The Plugins version
     */
    float version() default 1.0F;

    /**
     * The plugins authors
     */
    String[] author() default {};
}
