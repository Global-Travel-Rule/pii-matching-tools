/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/17 18:23
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * token set matching algorithm executor
 *
 * @author Global Travel Rule developer
 * @version 1.0.5
 * @since 2026/3/17 18:23
 * */
public class GlobalTravelRuleTokenSetMatchingExecutor extends GlobalTravelRuleBaseMatchingExecutor{

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.TOKEN_SET_RATIO;
    }

    @Override
    protected float computeSimilarity(String source, String target) {
        return FuzzySearch.tokenSetRatio(source, target);
    }
}
