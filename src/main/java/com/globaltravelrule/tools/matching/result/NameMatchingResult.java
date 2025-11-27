/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 17:13
 */

package com.globaltravelrule.tools.matching.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * name matching result
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/15 17:13
 *
 */
public class NameMatchingResult {

    private final Logger log = LoggerFactory.getLogger(NameMatchingResult.class);

    /**
     * highest matching rate
     *
     */
    private Float matchingRate;

    /**
     * matched or not
     *
     */
    private Boolean matched;

    private List<MatchingResult> nameMatchingStackTrace;

    public NameMatchingResult() {

    }

    public NameMatchingResult(Float matchingRate, Boolean matched) {
        this.matchingRate = matchingRate;
        this.matched = matched;
    }

    public NameMatchingResult(Float matchingRate, Boolean matched, List<MatchingResult> nameMatchingStackTrace) {
        this.matchingRate = matchingRate;
        this.matched = matched;
        this.nameMatchingStackTrace = nameMatchingStackTrace;
    }

    public Float getMatchingRate() {
        return matchingRate;
    }

    public void setMatchingRate(Float matchingRate) {
        this.matchingRate = matchingRate;
    }

    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public List<MatchingResult> getNameMatchingStackTrace() {
        return nameMatchingStackTrace;
    }

    public void setNameMatchingStackTrace(List<MatchingResult> nameMatchingStackTrace) {
        this.nameMatchingStackTrace = nameMatchingStackTrace;
    }

    public void printMatchingStackTrace() {
        if (nameMatchingStackTrace == null) {
            return;
        }
        for (int i = 0; i < nameMatchingStackTrace.size(); i++) {
            log.info("matching {} -> \n name : {} || matchingName : {} \n processedName:{} || processedMatchingName : {} \n matchingRate : {} \n",
                    i,
                    getNameMatchingStackTrace().get(i).getSource(),
                    getNameMatchingStackTrace().get(i).getTarget(),
                    getNameMatchingStackTrace().get(i).getProcessedSource(),
                    getNameMatchingStackTrace().get(i).getProcessedTarget(),
                    getNameMatchingStackTrace().get(i).getMatchingRate());
        }
    }
}
