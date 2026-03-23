/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/17 17:19
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * mixed global travel rule name matching executor
 * with sorting, partial and simple ratio matching,
 *
 * @author Global Travel Rule developer
 * @version 1.0.5
 * @since 2026/3/17 17:19
 *
 */
public class GlobalTravelRuleMixedMatchingExecutor extends GlobalTravelRuleBaseMatchingExecutor {

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.MIXED;
    }

    @Override
    protected float computeSimilarity(String source, String target) {
        // Use partialRatio which finds the best matching substring
        float partialRatio = FuzzySearch.ratio(source, target) / 100.0f;
        // Also try token set ratio which compares unique tokens
        float tokenSetRatio = FuzzySearch.tokenSortPartialRatio(source, target) / 100.0f;
        // Get the better score
        return Math.max(partialRatio, tokenSetRatio);
    }
}
