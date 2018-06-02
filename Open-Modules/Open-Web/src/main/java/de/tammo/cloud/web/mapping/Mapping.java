/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.web.mapping;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapping {

	String path();

}
