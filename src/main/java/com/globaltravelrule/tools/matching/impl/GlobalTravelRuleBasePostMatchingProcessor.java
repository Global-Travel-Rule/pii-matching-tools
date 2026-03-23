/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/16 10:14
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.PostMatchingProcessor;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

/**
 * base post matching processor
 *
 * @author Global Travel Rule developer
 * @version 1.0.5
 * @since 2026/3/16 10:14
 *
 */
public class GlobalTravelRuleBasePostMatchingProcessor implements PostMatchingProcessor {

    /**
     * process the result after matching
     *
     * @param result    the result of the matching
     * @param newResult the new result after matching
     *
     */
    protected void processTotalResult(NameMatchingResult result, NameMatchingResult newResult) {
        //Go to the maximum matching rate of the results
        if (result.getMatchingRate() < newResult.getMatchingRate()) {
            result.setMatchingRate(newResult.getMatchingRate());
        }
        if (newResult.getMatched()) {
            result.setMatched(newResult.getMatched());
        }
        //Set total matching results
        if (newResult.getNameMatchingStackTrace() != null && !newResult.getNameMatchingStackTrace().isEmpty()) {
            result.getNameMatchingStackTrace().addAll(newResult.getNameMatchingStackTrace());
        }
    }

    protected NameMatchingOptions generateNewOptionsWithoutNamesAndPostProcessors(NameMatchingOptions options) {
        NameMatchingOptions newOptions = new NameMatchingOptions();
        newOptions.setThreshold(options.getThreshold());
        newOptions.setNameProcessors(options.getNameProcessors());
        return newOptions;
    }
}
