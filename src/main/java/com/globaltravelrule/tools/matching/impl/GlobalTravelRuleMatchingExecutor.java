/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/27 18:34
 */

package com.globaltravelrule.tools.matching.impl;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.globaltravelrule.tools.matching.api.MatchingExecutor;
import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import com.globaltravelrule.tools.matching.exceptions.MatchingException;
import com.globaltravelrule.tools.matching.options.MatchingOptions;
import com.globaltravelrule.tools.matching.result.MatchingResult;
import com.globaltravelrule.tools.matching.utils.NamesUtils;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default global travel rule name matching executor
 * by fuzzy matching with token sorting
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/09/05 08:29
 *
 */
public class GlobalTravelRuleMatchingExecutor implements MatchingExecutor {

    private static final Logger log = LoggerFactory.getLogger(GlobalTravelRuleMatchingExecutor.class);

    @Override
    public MatchingAlgorithm getMatchingAlgorithm() {
        return MatchingAlgorithm.DEFAULT;
    }

    @Override
    public MatchingResult matching(MatchingOptions options) throws MatchingException {
        String compareSource = NamesUtils.namesToString(options.getSource());
        String compareTarget = NamesUtils.namesToString(options.getTarget());

        if (compareSource.isEmpty() || compareTarget.isEmpty()) {
            return new MatchingResult(
                    NamesUtils.namesToString(options.getSource()),
                    NamesUtils.namesToString(options.getTarget()), 0f);
        }
        //check any matching logic
        if (compareSource.equals(compareTarget)) {
            return new MatchingResult(compareSource, compareTarget, 1.0f);
        }

        String simplifyCompareSource = compareSource;
        if (hasChineseCharacter(compareSource)) {
            simplifyCompareSource = simplifyChineseCharacters(compareSource);
        }
        String simplifyCompareTarget = compareTarget;
        if (hasChineseCharacter(compareTarget)) {
            simplifyCompareTarget = simplifyChineseCharacters(compareTarget);
        }

        if (simplifyCompareSource.equals(simplifyCompareTarget)) {
            return new MatchingResult(compareSource, compareTarget, 1.0f);
        }
        //fuzzy matching logic
        float similarity = FuzzySearch.tokenSortRatio(simplifyCompareSource, simplifyCompareTarget) / 100.0f;
        return new MatchingResult(compareSource, compareTarget, similarity);
    }

    protected boolean hasChineseCharacter(String str) {
        try {
            return ZhConverterUtil.isChinese(str);
        } catch (Exception ex) {
            log.error("check chinese character error", ex);
            return false;
        }
    }

    protected String simplifyChineseCharacters(String str) {
        try {
            return ZhConverterUtil.toSimple(str);
        } catch (Exception ex) {
            log.error("simplify chinese characters error", ex);
            return str;
        }
    }
}
