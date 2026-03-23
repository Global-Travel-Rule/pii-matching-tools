/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/12/8 09:18
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.MatchingUtils;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * ignore middle name implementation of PostMatchingProcessor
 * do rematch when failed to achieve the target matching rate
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/12/08 09:18
 *
 */
public class GlobalTravelRuleIgnoreMiddleNamePostMatchingProcessor extends GlobalTravelRuleBasePostMatchingProcessor {

    private static final int MIDDLE_NAME_INDEX = 1;

    private static final int MIN_PROCESS_NAME_SIZE = 2;

    @Override
    public NameMatchingResult processMatch(NameMatchingOptions options, NameMatchingResult result) {
        //do rematch logic
        if (!result.getMatched() && result.getMatchingRate() > 0f) {
            //if the name has a middle name, remove the middle name and match again
            //source
            boolean nameChanged = false;
            List<List<String>> newNames = new ArrayList<>();
            for (List<String> name : options.getNames()) {
                List<String> newName = new ArrayList<>(name);
                if (processName(newName)) {
                    nameChanged = true;
                }
                newNames.add(newName);
            }
            //target
            List<List<String>> newMatchingNames = new ArrayList<>();
            for (List<String> matchingName : options.getMatchingNames()) {
                List<String> newMatchingName = new ArrayList<>(matchingName);
                if (processName(newMatchingName)) {
                    nameChanged = true;
                }
                newMatchingNames.add(newMatchingName);
            }
            if (nameChanged) {
                NameMatchingOptions newOptions = generateNewOptionsWithoutNamesAndPostProcessors(options);
                newOptions.setThreshold(options.getThreshold());
                newOptions.setNames(newNames);
                newOptions.setMatchingNames(newMatchingNames);
                NameMatchingResult newResult = MatchingUtils.matchingNames(newOptions);
                processTotalResult(result, newResult);
            } else {
                return result;
            }
        }
        return result;
    }

    boolean processName(List<String> names) {
        if (names.size() > MIN_PROCESS_NAME_SIZE
                && names.get(MIDDLE_NAME_INDEX) != null && !names.get(MIDDLE_NAME_INDEX).isEmpty()) {
            names.remove(1);
            return true;
        }
        return false;
    }
}
