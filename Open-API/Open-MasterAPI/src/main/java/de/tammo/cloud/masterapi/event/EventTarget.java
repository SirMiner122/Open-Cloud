/*
 * Copyright (c) 2018. File created by Tammo
 */

package de.tammo.cloud.masterapi.event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventTarget {}
