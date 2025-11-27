/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 17:28
 */

package com.globaltravelrule.tools.matching.options;

import com.globaltravelrule.tools.matching.api.NameProcessor;
import com.globaltravelrule.tools.matching.impl.GlobalTravelRuleNameProcessor;

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
     * Names to be matched
     * subitem of name is a list of strings, representing the different formats of the name (first name, middle name, last name, etc.)
     *
     */
    private List<List<String>> names;

    /**
     * The name of the reference to be matched, such as the KYC name of the business system
     * subitem of name is a list of strings, representing the different formats of the name (first name, middle name, last name, etc.)
     *
     */
    private List<List<String>> matchingNames;

    private Float threshold;

    private String algorithmType = "default";

    private List<NameProcessor> nameProcessors = new ArrayList<>();

    public NameMatchingOptions() {
    }

    public NameMatchingOptions(List<List<String>> names, List<List<String>> matchingNames) {
        this.names = names;
        this.matchingNames = matchingNames;
        this.nameProcessors.add(new GlobalTravelRuleNameProcessor());
    }

    public NameMatchingOptions(List<List<String>> names, List<List<String>> matchingNames, Float threshold) {
        this(names, matchingNames);
        this.threshold = threshold;
    }

    public List<List<String>> getNames() {
        return names;
    }

    public void setNames(List<List<String>> names) {
        this.names = names;
    }

    public List<List<String>> getMatchingNames() {
        return matchingNames;
    }

    public void setMatchingNames(List<List<String>> matchingNames) {
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

    public List<NameProcessor> getNameProcessors() {
        return nameProcessors;
    }

    public void setNameProcessors(List<NameProcessor> nameProcessors) {
        this.nameProcessors = nameProcessors;
    }
}
