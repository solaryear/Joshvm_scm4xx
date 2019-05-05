/*
 *
 *
 * Copyright  1990-2007 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions.
 */

#include <kni.h>

/**
 * Native method void init() of
 * com.sun.midp.jsr075.Initializer.
 * <p>
 * Do the basic initialization of the JSR75.
 * Registers a callback on a midlet suite removal.
 */
KNIEXPORT KNI_RETURNTYPE_VOID
Java_com_sun_cldc_jsr075_Initializer_init() {
    KNI_ReturnVoid();
}

/**
 * Native method void cleanup() of
 * com.sun.midp.jsr075.Initializer.
 * <p>
 * Finalizes the JSR75 subsystem.
 * Unregisters the callback on a midlet suite removal.
 */
KNIEXPORT KNI_RETURNTYPE_VOID
Java_com_sun_cldc_jsr075_Initializer_cleanup() {
    KNI_ReturnVoid();
}
