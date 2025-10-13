/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/27 19:06
 */

package com.globaltravelrule.tools.matching.exceptions;

/**
 * matching exception
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/27 19:06
 *
 */
public class MatchingException extends RuntimeException {

    public MatchingException(String message) {
        super(message);
    }

    public MatchingException(String message, Throwable cause) {
        super(message, cause);
    }
}