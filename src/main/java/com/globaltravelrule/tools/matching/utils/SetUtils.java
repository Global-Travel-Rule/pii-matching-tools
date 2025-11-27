/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/11/14 16:56
 */

package com.globaltravelrule.tools.matching.utils;

import java.util.Set;

/**
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/11/14 16:56
 *
 */
public class SetUtils {

    public static String nameSetToString(Set<String> nameSet){
        if (nameSet == null || nameSet.isEmpty()){
            return "";
        }
        return String.join(" ", nameSet);
    }
}
