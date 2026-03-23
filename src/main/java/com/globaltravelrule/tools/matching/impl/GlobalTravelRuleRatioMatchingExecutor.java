/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/17 17:16
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * simple global travel rule name matching executor by fuzzy matching
 * @author Global Travel Rule developer
 * @version 1.0.5
 * @since  2026/3/17 17:16
 * */
public class GlobalTravelRuleRatioMatchingExecutor extends GlobalTravelRuleBaseMatchingExecutor {

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.RATIO;
    }

    @Override
    protected float computeSimilarity(String source, String target) {
        //simple fuzzy matching logic
        return FuzzySearch.ratio(source, target) / 100.0f;
    }
}
