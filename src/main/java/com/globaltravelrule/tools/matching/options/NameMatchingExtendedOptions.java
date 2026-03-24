/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/10/20 14:00
 */

package com.globaltravelrule.tools.matching.options;

/**
 * Extended configuration options for name matching.
 * Provides additional feature flags beyond the core matching parameters.
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/10/20 14:00
 */
public class NameMatchingExtendedOptions {

    /**
     * When true, enables the entity name variation processor which removes
     * common legal entity suffixes (Ltd, LLC, Pte Ltd, Inc, etc.) and their
     * variations from name strings before matching. Also converts full-width
     * characters to half-width.
     */
    private boolean enableReplaceEntityNameVariations = false;

    public NameMatchingExtendedOptions() {
    }

    public NameMatchingExtendedOptions(boolean enableReplaceEntityNameVariations) {
        this.enableReplaceEntityNameVariations = enableReplaceEntityNameVariations;
    }

    public boolean isEnableReplaceEntityNameVariations() {
        return enableReplaceEntityNameVariations;
    }

    public void setEnableReplaceEntityNameVariations(boolean enableReplaceEntityNameVariations) {
        this.enableReplaceEntityNameVariations = enableReplaceEntityNameVariations;
    }
}
