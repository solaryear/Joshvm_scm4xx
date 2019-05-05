/*
 * Copyright (C) Max Mu
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License version 2 for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * Please visit www.joshvm.org if you need additional information or
 * have any questions.
 */
#ifndef __JAVACALL_DIO_H
#define __JAVACALL_DIO_H

#ifdef __cplusplus
extern "C" {
#endif


/**
 * @file javacall_dio.h
 * @ingroup DeviceAccess
 * @brief common definitions used for all DIO
 *
 */

/**
 * @defgroup DeviceAccess DIO API
 * @{
 */

/**
 * Device supported byte/bit ordering
 */
typedef enum {
    DAAPI_DEFAULT_ORDER = -1,
    /** Little-endian byte ordering */
    DAAPI_LITTLE_ENDIAN = 0, 
    /** Big-endian byte ordering */
    DAAPI_BIG_ENDIAN = 1,
    /** Mixed-endian (nonstandard) byte ordering */
    DAAPI_MIXED_ENDIAN = 2
} javacall_byteorder;


/**
 * @brief invalid ID (ID is assumed to be positive integer)
 */
#define JAVACALL_DEVICEACCESS_INVALID_ID -1

/**
 * PeripheralConfig.DEFAULT constant
 */
#define PERIPHERAL_CONFIG_DEFAULT -1



/**
 * DeviceAccess error codes
 */
typedef enum {
    JAVACALL_DIO_OK = 0,
    JAVACALL_DIO_FAIL,
    JAVACALL_DIO_NOT_FOUND,
    JAVACALL_DIO_BUSY,
    JAVACALL_DIO_CLOSED,
    JAVACALL_DIO_INVALID_CONFIG,
    JAVACALL_DIO_UNSUPPORTED_ACCESS_MODE,
    JAVACALL_DIO_WOULD_BLOCK,
    JAVACALL_DIO_INVALID_STATE,
    JAVACALL_DIO_UNSUPPORTED_OPERATION,
    JAVACALL_DIO_OUT_OF_MEMORY
} javacall_dio_result;

/** @} */

#ifdef __cplusplus
}
#endif

#endif /*__JAVACALL_DIO_H */
