/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/17 17:24
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.MatchingExecutor;
import com.globaltravelrule.tools.matching.exceptions.MatchingException;
import com.globaltravelrule.tools.matching.options.MatchingOptions;
import com.globaltravelrule.tools.matching.result.MatchingResult;
import com.globaltravelrule.tools.matching.utils.NamesUtils;

/**
 * base class for matching executor,
 * with basic logic for processing name strings and returning the result.
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2026/3/17 17:24
 *
 */
public abstract class GlobalTravelRuleBaseMatchingExecutor implements MatchingExecutor {

    @Override
    public MatchingResult matching(MatchingOptions options) throws MatchingException {
        String compareSource = NamesUtils.namesToString(options.getSource());
        String compareTarget = NamesUtils.namesToString(options.getTarget());

        if (compareSource.isEmpty() || compareTarget.isEmpty()) {
            return new MatchingResult(NamesUtils.namesToString(options.getSource()), NamesUtils.namesToString(options.getTarget()), 0f);
        }
        //check any matching logic
        if (compareSource.equals(compareTarget)) {
            return new MatchingResult(compareSource, compareTarget, 1.0f);
        }

        String simplifyCompareSource = compareSource;
        if (NamesUtils.hasChineseCharacter(compareSource)) {
            simplifyCompareSource = NamesUtils.simplifyChineseCharacters(compareSource);
        }
        String simplifyCompareTarget = compareTarget;
        if (NamesUtils.hasChineseCharacter(compareTarget)) {
            simplifyCompareTarget = NamesUtils.simplifyChineseCharacters(compareTarget);
        }

        if (simplifyCompareSource.equals(simplifyCompareTarget)) {
            return new MatchingResult(compareSource, compareTarget, 1.0f);
        }

        float similarity = computeSimilarity(simplifyCompareSource, simplifyCompareTarget);
        return new MatchingResult(compareSource, compareTarget, similarity);
    }

    /**
     * compute similarity between source and target
     *
     * @param source source name
     * @param target target name
     * @return similarity score
     *
     */
    abstract protected float computeSimilarity(String source, String target);
}
