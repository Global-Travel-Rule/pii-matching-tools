/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 16:56
 */

package com.globaltravelrule.tools.matching.api;

import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

/**
 * matching name post processor interface
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/12/05 17:28
 */
public interface PostMatchingProcessor {

    /**
     * post process the name matching result
     *
     * @param options the name matching options
     * @param result  the name matching result
     * @return the new processed name matching result
     */
    default NameMatchingResult processMatch(NameMatchingOptions options, NameMatchingResult result) {
        return result;
    }
}
