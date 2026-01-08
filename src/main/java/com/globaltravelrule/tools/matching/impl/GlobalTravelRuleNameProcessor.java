/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 17:02
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.NameProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * default global travel rule name processor
 * <p>
 * 移除()
 * [-.,\p{Z}\s]+ 仅处理常见标点和空白,\p{Z} 和 \s 覆盖 Unicode 分隔符与空白；用 + 合并成一个空格
 * 最后 trim 去除首尾多余空格
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/05 09:29
 */
public class GlobalTravelRuleNameProcessor implements NameProcessor {

    public static final String SYMBOL_NEED_TO_REPLACE_REGEX = "[-.,\\p{Z}\\s]+";

    public static final String NAMES_SPLITER = " ";

    @Override
    public List<String> processName(List<String> nameItems) {
        if (nameItems == null || nameItems.isEmpty()) {
            return nameItems;
        }
        List<String> processedNameItems = new ArrayList<>();
        for (String nameItem : nameItems) {
            String processedNameItem = Optional.ofNullable(nameItem).map(t ->
                    t.toLowerCase().replaceAll(SYMBOL_NEED_TO_REPLACE_REGEX, NAMES_SPLITER).trim())
                    .orElse("");
            processedNameItem = processedNameItem.replaceAll("[()（）]", "");
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
