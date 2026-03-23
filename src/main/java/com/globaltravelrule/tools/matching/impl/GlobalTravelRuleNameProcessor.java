/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 17:02
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.NameProcessor;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Default global travel rule name processor
 * Remove special characters
 * Only handle common punctuation and whitespace, merge+into one space
 * Finally, remove excess spaces at the beginning and end of the trim
 * Normalize Unicode characters to remove diacritical marks (accents)
 *
 * @author Global Travel Rule developer
 * @version 1.0.1
 * @since 2025/10/05 09:29
 */
public class GlobalTravelRuleNameProcessor implements NameProcessor {

    public static final String NAMES_SPLITER = " ";

    /**
     * Normalize Unicode characters by removing diacritical marks (accents).
     * This converts characters like ö→o, ñ→n, é→e, ü→u, etc.
     * Uses NFD (Canonical Decomposition) to separate base characters from combining marks,
     * then removes all combining diacritical marks.
     *
     * @param text the text to normalize
     * @return normalized text without diacritical marks
     */
    private String normalizeUnicode(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        // NFD = Canonical Decomposition (é -> e + ´)
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        // Remove combining diacritical marks (\p{M} = Mark category in Unicode)
        return normalized.replaceAll("\\p{M}", "");
    }

    @Override
    public List<String> processName(List<String> nameItems, NameMatchingOptions options) {
        if (nameItems == null || nameItems.isEmpty()) {
            return nameItems;
        }
        List<String> processedNameItems = new ArrayList<>();
        for (String nameItem : nameItems) {
            if (nameItem == null || nameItem.isEmpty()) {
                continue;
            }
            String processedNameItem = Optional.of(nameItem)
                    .map(this::normalizeUnicode)  // Step 1: Remove diacritical marks
                    .map(t -> t.toLowerCase().replaceAll(options.getReplaceRegex(), NAMES_SPLITER).trim())  // Step 2: Lowercase and replace separators
                    .orElse("");
            processedNameItem = processedNameItem.replaceAll(options.getRemoveRegex(), "");  // Step 3: Remove unwanted characters
            if (!processedNameItem.isEmpty()) {
                if (processedNameItem.contains(NAMES_SPLITER)) {
                    processedNameItems.addAll(Arrays.asList(processedNameItem.split(NAMES_SPLITER)));
                } else {
                    processedNameItems.add(processedNameItem);
                }
            }
        }
        return processedNameItems;
    }
}
