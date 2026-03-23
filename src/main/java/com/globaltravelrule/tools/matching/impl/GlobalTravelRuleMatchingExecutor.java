/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/27 18:34
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * default global travel rule name matching executor
 * by fuzzy matching with token sorting
 *
 * @author Global Travel Rule developer
 * @version 1.0.1
 * @since 2025/09/05 08:29
 *
 */
public class GlobalTravelRuleMatchingExecutor extends GlobalTravelRuleBaseMatchingExecutor {

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.DEFAULT;
    }

    /**
     * compute similarity between source and target
     *
     * @param source source name
     * @param target target name
     * @return similarity score
     *
     */
    public float computeSimilarity(String source, String target) {
        //fuzzy matching logic
        return FuzzySearch.tokenSortRatio(source, target) / 100.0f;
    }
}
