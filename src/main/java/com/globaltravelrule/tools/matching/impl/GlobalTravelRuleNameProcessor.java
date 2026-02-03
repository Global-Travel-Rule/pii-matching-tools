/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 17:02
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.NameProcessor;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * default global travel rule name processor
 * <p>
 * Remove special characters
 * Only handle common punctuation and whitespace, merge+into one space
 * Finally, remove excess spaces at the beginning and end of the trim
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/05 09:29
 */
public class GlobalTravelRuleNameProcessor implements NameProcessor {

    public static final String NAMES_SPLITER = " ";

    @Override
    public List<String> processName(List<String> nameItems, NameMatchingOptions options) {
        if (nameItems == null || nameItems.isEmpty()) {
            return nameItems;
        }
        List<String> processedNameItems = new ArrayList<>();
        for (String nameItem : nameItems) {
            String processedNameItem = Optional.ofNullable(nameItem).map(t ->
                            t.toLowerCase().replaceAll(options.getReplaceRegex(), NAMES_SPLITER).trim())
                    .orElse("");
            processedNameItem = processedNameItem.replaceAll(options.getRemoveRegex(), "");
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
