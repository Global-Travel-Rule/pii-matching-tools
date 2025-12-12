/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 17:28
 */

package com.globaltravelrule.tools.matching.options;

import java.util.List;

/**
 * matching options
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/15 17:28
 *
 */
public class MatchingOptions {

    private List<String> source;

    private List<String> target;

    public MatchingOptions(List<String> source, List<String> target) {
        this.source = source;
        this.target = target;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }
}
