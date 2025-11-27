/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 17:28
 */

package com.globaltravelrule.tools.matching.options;

import java.util.Set;

/**
 * matching options
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/15 17:28
 *
 */
public class MatchingOptions {

    private Set<String> source;

    private Set<String> target;

    public MatchingOptions(Set<String> source,Set<String> target) {
        this.source = source;
        this.target = target;
    }

    public Set<String> getSource() {
        return source;
    }

    public void setSource(Set<String> source) {
        this.source = source;
    }

    public Set<String> getTarget() {
        return target;
    }

    public void setTarget(Set<String> target) {
        this.target = target;
    }
}
