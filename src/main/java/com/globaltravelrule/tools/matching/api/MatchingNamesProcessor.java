/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 16:56
 */

package com.globaltravelrule.tools.matching.api;

/**
 * process matching names, before applying the matching logic.
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/05 17:28
 *
 *
 */
public interface MatchingNamesProcessor {

    /**
     * process matching names, before applying the matching logic.
     *
     * @param matchingName the name to process
     * @return processed name
     */
    default String processMatchingNames(String matchingName) {
        return matchingName;
    }
}
