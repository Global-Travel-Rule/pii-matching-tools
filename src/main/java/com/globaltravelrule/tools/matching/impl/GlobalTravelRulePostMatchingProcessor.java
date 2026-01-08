/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/12/8 09:18
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.MatchingUtils;
import com.globaltravelrule.tools.matching.api.PostMatchingProcessor;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.MatchingResult;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * default implementation of PostMatchingProcessor
 * do rematch when failed to achieve the target matching rate
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/12/08 09:18
 *
 */
public class GlobalTravelRulePostMatchingProcessor implements PostMatchingProcessor {

    @Override
    public NameMatchingResult processMatch(NameMatchingOptions options, NameMatchingResult result) {
        //do rematch logic
        NameMatchingResult newResult = result;
        if (!result.getMatched() && result.getMatchingRate() > 0f) {
            //if the name has a middle name, remove the middle name and match again
            //source
            List<List<String>> newNames = new ArrayList<>();
            for (List<String> name : options.getNames()) {
                List<String> newName = new ArrayList<>(name);
                if (newName.size() > 2) {
                    newName.remove(1);
                }
                newNames.add(newName);
            }
            options.setNames(newNames);
            //target
            List<List<String>> newMatchingNames = new ArrayList<>();
            for (List<String> matchingName : options.getMatchingNames()) {
                List<String> newMatchingName = new ArrayList<>(matchingName);
                if (newMatchingName.size() > 2) {
                    newMatchingName.remove(1);
                }
                newMatchingNames.add(newMatchingName);
            }
            options.setMatchingNames(newMatchingNames);
            newResult = MatchingUtils.getInstance().doMatchingNames(options);
            //Go to the maximum matching rate of the results
            if (result.getMatchingRate() > newResult.getMatchingRate()) {
                newResult.setMatchingRate(result.getMatchingRate());
            }
            //Set total matching results
            List<MatchingResult> totalNameMatchingStackTraces = new ArrayList<>(
                    result.getNameMatchingStackTrace().size() + newResult.getNameMatchingStackTrace().size()
            );
            totalNameMatchingStackTraces.addAll(result.getNameMatchingStackTrace());
            totalNameMatchingStackTraces.addAll(newResult.getNameMatchingStackTrace());
            newResult.setNameMatchingStackTrace(totalNameMatchingStackTraces);
        }
        return newResult;
    }
}
