/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/16 08:27
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.MatchingUtils;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

import java.util.*;

/**
 * disorder post matching processor
 * will disorder the names and rematch them
 *
 * @author Global Travel Rule
 * @version 1.0.5
 * @since 2022/3/16 08:27
 *
 * */
public class GlobalTravelRuleDisorderPostMatchingProcessor extends GlobalTravelRuleBasePostMatchingProcessor {

    @Override
    public NameMatchingResult processMatch(NameMatchingOptions options, NameMatchingResult result) {
        //do disorder rematch logic
        if (!result.getMatched() && result.getMatchingRate() > 0f) {
            List<List<String>> disorderedNames = processNames(options.getNames());
            List<List<String>> disorderedMatchingNames = processNames(options.getMatchingNames());
            NameMatchingOptions newOptions = generateNewOptionsWithoutNamesAndPostProcessors(options);
            newOptions.setNames(disorderedNames);
            newOptions.setMatchingNames(disorderedMatchingNames);
            NameMatchingResult newResult = MatchingUtils.matchingNames(newOptions);
            processTotalResult(result, newResult);
        }
        return result;
    }

    protected List<List<String>> processNames(List<List<String>> input) {
        if (input == null) {
            return new ArrayList<>();
        }
        // Use Set for global deduplication (List's equals/hashCode will be compared by element)
        Set<List<String>> out = new LinkedHashSet<>();
        for (List<String> one : input) {
            out.addAll(permute(one));
        }
        return new ArrayList<>(out);
    }

    // Single sub list: vacant positions are not important;
    // Perform deduplication and full permutation on non-empty elements; Place empty spaces at the end uniformly
    private static List<List<String>> permute(List<String> one) {
        if (one == null) {
            return new ArrayList<>();
        }
        int blankCount = 0;
        List<String> words = new ArrayList<>();
        for (String s : one) {
            if (s == null || s.isEmpty()) blankCount++;
            else words.add(s);
        }

        // All empty: only one representative
        if (words.isEmpty()) {
            List<String> only = new ArrayList<>();
            for (int i = 0; i < blankCount; i++) {
                only.add("");
            }
            return Collections.singletonList(only);
        }
        List<List<String>> perms = permuteUnique(words);
        List<List<String>> res = new ArrayList<>(perms.size());
        for (List<String> p : perms) {
            List<String> rep = new ArrayList<>(p.size() + blankCount);
            rep.addAll(p);
            for (int i = 0; i < blankCount; i++) rep.add("");
            res.add(rep);
        }
        return res;
    }

    // Deduce full arrangement (elements may be duplicated)
    private static List<List<String>> permuteUnique(List<String> arr) {
        List<String> nums = new ArrayList<>(arr);
        nums.sort(Comparator.naturalOrder());
        boolean[] used = new boolean[nums.size()];
        Deque<String> path = new ArrayDeque<>();
        List<List<String>> res = new ArrayList<>();
        dfs(nums, used, path, res);
        return res;
    }

    private static void dfs(List<String> nums, boolean[] used, Deque<String> path, List<List<String>> res) {
        if (path.size() == nums.size()) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.size(); i++) {
            if (used[i]) continue;
            if (i > 0 && Objects.equals(nums.get(i), nums.get(i - 1)) && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            path.addLast(nums.get(i));
            dfs(nums, used, path, res);
            path.removeLast();
            used[i] = false;
        }
    }
}
