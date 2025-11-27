/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/27 19:25
 */

package com.globaltravelrule.tools.matching.result;

/**
 * matching result
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/27 19:25
 *
 */
public class MatchingResult {

    /**
     * source content that to be compared
     *
     */
    private String source;

    /**
     * target content that to be matched
     *
     */
    private String target;


    /**
     * processed source content that to be compared
     *
     */
    private String processedSource;

    /**
     * processed target content that to be matched
     *
     */
    private String processedTarget;

    /**
     * matching rate
     * */
    private Float matchingRate;

    public MatchingResult(String processedSource, String processedTarget, Float matchingRate) {
        this.processedSource = processedSource;
        this.processedTarget = processedTarget;
        this.matchingRate = matchingRate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getProcessedSource() {
        return processedSource;
    }

    public void setProcessedSource(String processedSource) {
        this.processedSource = processedSource;
    }

    public String getProcessedTarget() {
        return processedTarget;
    }

    public void setProcessedTarget(String processedTarget) {
        this.processedTarget = processedTarget;
    }

    public Float getMatchingRate() {
        return matchingRate;
    }

    public void setMatchingRate(Float matchingRate) {
        this.matchingRate = matchingRate;
    }
}
