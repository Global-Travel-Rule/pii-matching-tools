/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/11/14 16:56
 */

package com.globaltravelrule.tools.matching.utils;

import java.util.Collection;

/**
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/11/14 16:56
 *
 */
public class NamesUtils {

    public static String namesToString(Collection<String> names){
        if (names == null || names.isEmpty()){
            return "";
        }
        return String.join(" ", names);
    }
}
