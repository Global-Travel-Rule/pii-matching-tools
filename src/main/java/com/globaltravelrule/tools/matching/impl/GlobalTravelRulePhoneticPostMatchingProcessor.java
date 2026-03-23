/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/16 18:50
 */

package com.globaltravelrule.tools.matching.impl;

import org.apache.commons.codec.language.DoubleMetaphone;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Phonetic post matching processor
 * Uses DoubleMetaphone algorithm to match names that sound similar
 * but are spelled differently (e.g., different romanizations of the same name)
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2026/3/16 18:50
 */
public class GlobalTravelRulePhoneticPostMatchingProcessor extends GlobalTravelRuleDisorderPostMatchingProcessor {

    private final DoubleMetaphone engine = new DoubleMetaphone();

    @Override
    protected List<List<String>> processNames(List<List<String>> nameList) {
        if (nameList == null) {
            return new ArrayList<>();
        }
        // Use Set for global deduplication (List's equals/hashCode will be compared by element)
        Set<List<String>> out = new LinkedHashSet<>();
        for (List<String> name : nameList) {
            out.add(namesToPhonetic(name));
        }
        return new ArrayList<>(out);
    }

    /**
     * Convert list of name lists to phonetic codes.
     * Filters out non-Latin characters as DoubleMetaphone works best with Latin scripts.
     *
     * @param names the names to convert
     * @return list of phonetic codes
     */
    private List<String> namesToPhonetic(List<String> names) {
        if (names == null) return new ArrayList<>();

        Set<String> tokens = new LinkedHashSet<>();

        for (String part : names) {
            if (part == null) continue;
            String p = part.trim();
            if (p.isEmpty()) continue;

            String encoded = engine.encode(p);
            if (encoded == null || encoded.isEmpty()) continue;

            // Compatibility with still containing separators
            for (String t : encoded.split("[|\\-;]+")) {
                String tt = t.trim();
                if (!tt.isEmpty()) tokens.add(tt);
            }
        }
        return new ArrayList<>(tokens);
    }
}
