/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/27 18:26
 */

package com.globaltravelrule.tools.matching.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * matching algorithm enum
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public enum MatchingAlgorithm {

    DEFAULT("default", "any match and fuzzy matching algorithm");

    private static final Map<String, MatchingAlgorithm> MAP;

    static {
        MAP = Arrays.stream(MatchingAlgorithm.values()).collect(Collectors.toMap(MatchingAlgorithm::getName, algorithm -> algorithm));
    }

    private final String name;

    private final String description;

    MatchingAlgorithm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static MatchingAlgorithm parse(String algorithmType) {
        return MAP.get(algorithmType);
    }
}
