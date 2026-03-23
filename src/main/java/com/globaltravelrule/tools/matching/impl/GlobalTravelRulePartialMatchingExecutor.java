/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/17 17:45
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * partial global travel rule name matching executor with best partial matching
 *
 * @author Global Travel Rule developer
 * @version 1.0.5
 * @since 2026/3/17 17:45
 *
 */
public class GlobalTravelRulePartialMatchingExecutor extends GlobalTravelRuleBaseMatchingExecutor {

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.PARTIAL_RATIO;
    }

    @Override
    protected float computeSimilarity(String source, String target) {
        // Use partialRatio which finds the best matching substring
        return FuzzySearch.partialRatio(source, target) / 100.0f;
    }
}
