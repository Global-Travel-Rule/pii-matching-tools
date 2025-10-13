/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 17:02
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.MatchingNamesProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * default global travel rule matching names processor
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/05 09:29
 *
 */
public class GlobalTravelRuleMatchingNamesProcessor implements MatchingNamesProcessor {

    public static final String SYMBOL_NEED_TO_REMOVE_REGEX = "[-,.\\s]";

    @Override
    public String processMatchingNames(String matchingName) {
        if (matchingName == null || matchingName.isEmpty()) {
            return matchingName;
        }
        //split matching name by blank
        String[] matchingNames = matchingName.split(" ");
        List<String> processedMatchingNames = new ArrayList<>();
        for (String name : matchingNames) {
            String processedName = Optional.ofNullable(name).map(t -> t.toLowerCase().replaceAll(SYMBOL_NEED_TO_REMOVE_REGEX, "")).orElse("").trim();
            processedName = processedName.toLowerCase().replaceAll("\\p{Z}+", "").trim();
            processedMatchingNames.add(processedName);
        }
        if (processedMatchingNames.isEmpty()) {
            return matchingName;
        } else {
            if (processedMatchingNames.size() == 1) {
                return processedMatchingNames.get(0);
            } else {
                return String.join(" ", processedMatchingNames);
            }
        }
    }
}
