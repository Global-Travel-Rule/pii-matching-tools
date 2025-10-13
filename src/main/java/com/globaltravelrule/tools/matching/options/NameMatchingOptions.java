/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 17:28
 */

package com.globaltravelrule.tools.matching.options;

import com.globaltravelrule.tools.matching.api.MatchingNamesProcessor;
import com.globaltravelrule.tools.matching.impl.GlobalTravelRuleMatchingNamesProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * matching options
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/15 17:28
 *
 */
public class NameMatchingOptions {

    /**
     * Name to be matched
     * */
    private List<String> names;

    /**
     * The name of the reference to be matched, such as the KYC name of the business system
     * */
    private List<String> matchingNames;

    private Float threshold;

    private String algorithmType = "default";

    private List<MatchingNamesProcessor> matchingNamesProcessors = new ArrayList<>();

    public NameMatchingOptions() {
    }

    public NameMatchingOptions(List<String> names, List<String> matchingNames) {
        this.names = names;
        this.matchingNames = matchingNames;
        this.matchingNamesProcessors.add(new GlobalTravelRuleMatchingNamesProcessor());
    }

    public NameMatchingOptions(List<String> names, List<String> matchingNames, Float threshold) {
        this(names,matchingNames);
        this.threshold = threshold;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getMatchingNames() {
        return matchingNames;
    }

    public void setMatchingNames(List<String> matchingNames) {
        this.matchingNames = matchingNames;
    }

    public Float getThreshold() {
        return threshold;
    }

    public void setThreshold(Float threshold) {
        this.threshold = threshold;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public List<MatchingNamesProcessor> getMatchingNamesProcessors() {
        return matchingNamesProcessors;
    }

    public void setMatchingNamesProcessors(List<MatchingNamesProcessor> matchingNamesProcessors) {
        this.matchingNamesProcessors = matchingNamesProcessors;
    }
}
