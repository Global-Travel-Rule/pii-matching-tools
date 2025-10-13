/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/16 14:53
 */

package com.globaltravelrule.tools.matching.api;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import com.globaltravelrule.tools.matching.exceptions.MatchingException;
import com.globaltravelrule.tools.matching.options.MatchingOptions;
import com.globaltravelrule.tools.matching.result.MatchingResult;

/**
 * matching executor
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/16 14:53
 *
 * */
public interface MatchingExecutor {

    /**
     * Execute the matching process.
     *
     * @param options the matching options
     * @return the matching result
     * @throws MatchingException if an error occurs during the matching process
     */
    default MatchingResult matching(MatchingOptions options) throws MatchingException {
        throw new MatchingException("Not supported");
    }

    /**
     * Get the matching algorithm registration enum.
     *
     * @return the matching algorithm
     */
    MatchingAlgorithm getMatchingAlgorithm();
}
