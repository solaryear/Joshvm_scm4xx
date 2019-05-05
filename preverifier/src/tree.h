/*
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

/*
 * Definitions having to do with the program tree
 */

#ifndef _TREE_H_
#define _TREE_H_

#include <oobj.h>    /* for the definition of unicode */
#include <typecodes.h>

extern int   SkipSourceChecks;
extern char *progname;
extern ClassClass **binclasses;
extern long nbinclasses, sizebinclasses;

/* User specifiable attributes */
#define ACC_PUBLIC            0x0001    /* visible to everyone */
#define ACC_PRIVATE           0x0002    /* visible only to the defining class */
#define ACC_PROTECTED         0x0004    /* visible to subclasses */
#define ACC_STATIC            0x0008    /* instance variable is static */
#define ACC_FINAL             0x0010    /* no further subclassing, overriding */
#define ACC_SYNCHRONIZED      0x0020    /* wrap method call in monitor lock */
#define ACC_SUPER             0x0020    /* funky handling of invokespecial */
#define ACC_THREADSAFE        0x0040    /* can cache in registers */
#define ACC_TRANSIENT         0x0080    /* not persistant */
#define ACC_NATIVE            0x0100    /* implemented in C */
#define ACC_INTERFACE         0x0200    /* class is an interface */
#define ACC_ABSTRACT          0x0400    /* no definition provided */
#define ACC_XXUNUSED1         0x0800    /*  */

#define ACC_WRITTEN_FLAGS     0x0FFF    /* flags actually put in .class file */

/* Other attributes */
#define ACC_VALKNOWN          0x1000    /* constant with known value */
#define ACC_DOCED             0x2000    /* documentation generated */
#define ACC_MACHINE_COMPILED  0x4000    /* compiled to machine code */
#define ACC_XXUNUSED3         0x8000    /*  */

#define IsPrivate(access) (((access) & ACC_PRIVATE) != 0)
#define IsPublic(access)  (((access) & ACC_PUBLIC) != 0)
#define IsProtected(access)  (((access) & ACC_PROTECTED) != 0)

char *addstr(char *s, char *buf, char *limit, char term);
char *addhex(long n, char *buf, char *limit);
char *adddec(long n, char *buf, char *limit);

#ifdef TRIMMED
# undef DEBUG
# undef STATS
# define NOLOG
#endif

#endif /* !_TREE_H_ */
