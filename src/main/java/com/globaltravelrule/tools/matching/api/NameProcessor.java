/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/7 16:56
 */

package com.globaltravelrule.tools.matching.api;

import java.util.List;

/**
 * process name, before applying the matching logic.
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/05 17:28
 */
public interface NameProcessor {

    /**
     * process name, before applying the matching logic.
     *
     * @param nameItems the name items to process (first name, middle name, last name, etc.)
     * @return processed nameItems
     */
    default List<String> processName(List<String> nameItems) {
        return nameItems;
    }
}
