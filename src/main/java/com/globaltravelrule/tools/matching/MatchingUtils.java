/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/15 09:29
 */

package com.globaltravelrule.tools.matching;

import com.globaltravelrule.tools.matching.api.MatchingExecutor;
import com.globaltravelrule.tools.matching.api.NameProcessor;
import com.globaltravelrule.tools.matching.enums.MatchingAlgorithm;
import com.globaltravelrule.tools.matching.exceptions.MatchingException;
import com.globaltravelrule.tools.matching.options.MatchingOptions;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.MatchingResult;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;
import com.globaltravelrule.tools.matching.utils.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * global travel rule name matching utils
 *
 * @author Global Travel Rule developer
 * @version 1.0.0
 * @since 2025/9/15 09:29
 *
 */
public class MatchingUtils {

    private static final Logger log = LoggerFactory.getLogger(MatchingUtils.class);

    private static volatile MatchingUtils instance;

    private final Map<MatchingAlgorithm, MatchingExecutor> MATCHING_ALGORITHMS_HOLDER;

    private MatchingUtils() {
        MATCHING_ALGORITHMS_HOLDER = new HashMap<>(16);
        ServiceLoader<MatchingExecutor> loader = ServiceLoader.load(MatchingExecutor.class);
        loader.forEach(executor -> {
            if (MATCHING_ALGORITHMS_HOLDER.containsKey(executor.getMatchingAlgorithm())) {
                throw new MatchingException("Duplicate matching algorithm: " + executor.getMatchingAlgorithm() + ", please check your matching implementation dependencies config.");
            }
            MATCHING_ALGORITHMS_HOLDER.put(executor.getMatchingAlgorithm(), executor);
        });
    }

    private static MatchingUtils getInstance() {
        if (instance == null) {
            synchronized (MatchingUtils.class) {
                if (instance == null) {
                    instance = new MatchingUtils();
                }
            }
        }
        return instance;
    }

    /**
     * name match logic
     *
     * @param options matching options
     * @return matching result
     * @throws MatchingException matching exception
     *
     */
    private List<MatchingResult> doMatchingNames(NameMatchingOptions options) {
        MatchingExecutor executor = getMatchingExecutor(options.getAlgorithmType());
        final List<Set<String>> namesList = new ArrayList<Set<String>>() {};
        if (options.getNames() != null){
            options.getNames().forEach(nameItems -> namesList.add(new HashSet<>(nameItems)));
        }
        final List<Set<String>> matchingNamesList = new ArrayList<>();
        if (options.getMatchingNames() != null){
            options.getMatchingNames().forEach(nameItems -> matchingNamesList.add(new HashSet<>(nameItems)));
        }

        final List<MatchingResult> resultList = new ArrayList<>();
        for (Set<String> nameSet  : namesList) {
            Set<String> processedNameSet = nameSet;
            //process name
            for (NameProcessor nameProcessor : options.getNameProcessors()) {
                processedNameSet = nameProcessor.processName(processedNameSet);
            }
            for (Set<String> matchingNameSet : matchingNamesList) {
                Set<String> processedMatchingNameSet = matchingNameSet;
                //process matching name
                for (NameProcessor nameProcessor : options.getNameProcessors()) {
                    processedMatchingNameSet = nameProcessor.processName(processedMatchingNameSet);
                }
                MatchingResult result = executor.matching(new MatchingOptions(processedNameSet, processedMatchingNameSet));
                result.setSource(SetUtils.nameSetToString(nameSet));
                result.setTarget(SetUtils.nameSetToString(matchingNameSet));
                resultList.add(result);
                if (options.getThreshold() != null && result.getMatchingRate() > options.getThreshold()) {
                    return resultList;
                }
            }
        }
        return resultList;
    }

    private MatchingExecutor getMatchingExecutor(String algorithmType) {
        MatchingAlgorithm algorithm = MatchingAlgorithm.parse(algorithmType);
        if (algorithm == null) {
            throw new MatchingException("Unsupported matching algorithm: " + algorithmType);
        }
        if (!MATCHING_ALGORITHMS_HOLDER.containsKey(algorithm)) {
            throw new MatchingException("Unsupported matching algorithm: " + algorithm + ", please check your matching implementation dependencies config.");
        }
        return MATCHING_ALGORITHMS_HOLDER.get(algorithm);
    }

    public static NameMatchingResult matchingNames(NameMatchingOptions options) {
        float matchingRate = 0f;
        boolean matched;
        if (options.getNames() == null || options.getNames().isEmpty() || options.getMatchingNames() == null || options.getMatchingNames().isEmpty() || options.getThreshold() == null) {
            log.warn("Invalid input parameters for name matching: names, matchingNames, or threshold cannot be null or empty!");
            return new NameMatchingResult(0f, false);
        }
        List<MatchingResult> resultList = MatchingUtils.getInstance().doMatchingNames(options);
        if (!resultList.isEmpty()) {
            Optional<MatchingResult> result = resultList.stream().max(Comparator.comparingDouble(MatchingResult::getMatchingRate));
            matchingRate = result.map(MatchingResult::getMatchingRate).orElse(0f);
        }
        matched = matchingRate >= options.getThreshold();
        return new NameMatchingResult(matchingRate, matched, resultList);
    }
}
