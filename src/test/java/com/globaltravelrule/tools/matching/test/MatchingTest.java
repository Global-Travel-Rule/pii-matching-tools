/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/9/28 21:57
 */

package com.globaltravelrule.tools.matching.test;

import com.globaltravelrule.tools.matching.MatchingUtils;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchingTest {

    public static final float THRESHOLD = 0.9f;

    @Test
    public void nameMatchTestNullCase1() {
        List<String> matchNames = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(null, matchNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNullCase2() {
        List<String> names = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, null, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNullCase3() {
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(null, null, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCase1() {
        List<String> names = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Tom", "Sean", "Allen"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCase2() {
        List<String> names = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Tom", "Bob"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCase3() {
        List<String> names = new ArrayList<>(Arrays.asList("", "Becky", "Tony"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Jon", "", "Allen"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestHasChineseCase1() {
        List<String> names = new ArrayList<>(List.of("杨阳"));
        List<String> matchingNames = new ArrayList<>(List.of("杨阳"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestHasChineseCase2() {
        List<String> names = new ArrayList<>(List.of("杨阳"));
        List<String> matchingNames = new ArrayList<>(List.of("楊陽"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestHasChineseCase3() {
        List<String> names = new ArrayList<>(List.of("黄御鉴"));
        List<String> matchingNames = new ArrayList<>(List.of("黃禦鑑"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase1() {
        // Chinese traditional
        List<String> names = new ArrayList<>(List.of("欧応勲"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("歐應勳", "欧应勋"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase2() {
        // Japanese
        List<String> names = new ArrayList<>(List.of("渡辺図広"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("渡边图广", "渡邊圖廣"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase3() {
        // Korean
        List<String> names = new ArrayList<>(List.of("한성훈"));
        List<String> matchingNames = new ArrayList<>(List.of("韩成勋"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase4() {
        // Korean
        List<String> names = new ArrayList<>(List.of("한성훈"));
        List<String> matchingNames = new ArrayList<>(List.of("한철희"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase5() {
        // Korean
        List<String> names = new ArrayList<>(List.of("한선희"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("韩成勋", "한선희"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase6() {
        // Arabic
        List<String> names = new ArrayList<>(List.of("فهد عبدالعزيز الشمري"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Fahd Abdulaziz al-Shammari", "فهد عبدالعزيز الشمري"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase7() {
        // Arabic
        List<String> names = new ArrayList<>(List.of("فهد عبدالعزيز الشمري"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("محمد بن سلمان بن امین", "Muhammad bin Salman bin Amin"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase8() {
        // Russian
        List<String> names = new ArrayList<>(List.of("Святополк"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Святополк", "Sviatopolk"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase9() {
        // Russian
        List<String> names = new ArrayList<>(List.of("Святополк"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("Мстислав", "Mstislav"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase10() {
        // Thai
        List<String> names = new ArrayList<>(List.of("จารุณี"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("จารุณี", "Jarunee"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase11() {
        // Thai
        List<String> names = new ArrayList<>(List.of("จารุณี"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("จันจิรา", "Janjira"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase1() {
        List<String> names = new ArrayList<>(Arrays.asList("Bo MrsLi", "小饼邱"));
        List<String> matchingNames = new ArrayList<>(List.of("boli"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase2() {
        List<String> names = new ArrayList<>(Arrays.asList(
                "vincenzopulera'",
                "pulera'vincenzo",
                "PuleráVincenzo",
                "pulerávincenzo",
                "VincenzoPulerá",
                "vincenzopulerá"
        ));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("vincenzopulerà", "vincenzopulerà"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase3() {
        List<String> names = new ArrayList<>(Arrays.asList(
                "simonesilviacooperforster",
                "cooperforstersimonesilvia",
                "coopersimone",
                "CooperSimone",
                "simonecooper",
                "SimoneCooper"
        ));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("simonesilviacooper", "coopersimonesilvia"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase4() {
        // not match
        List<String> names = new ArrayList<>(Arrays.asList("litzuhui", "LiTzu Hui", "tzuhuili", "Tzu HuiLi"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("tzu-huili", "litzu-hui"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase5() {
        // not match 0.8
        List<String> names = new ArrayList<>(Arrays.asList("bùiđuccanh", "canhbuiduc", "CANHBUIDUC", "buiduccanh", "BUIDUCCANH"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("buiduccanh", "bùiđứccảnh", "bùiđứccảnh", "buiduccanh"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase6() {
        // not match 0.8
        List<String> names = new ArrayList<>(Arrays.asList(
                "tudor-andrei vilceanu",
                "tudorandrei vilceanu",
                "vilceanu tudorandrei",
                "vilceanu tudor-andrei",
                "Tudor-Andrei Vilceanu",
                "Vilceanu Tudor-Andrei"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("tudorvilceanu", "tudorvilceanu"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase7() {
        // not match 0.8
        List<String> names = new ArrayList<>(Arrays.asList(
                "tudor-andrei vilceanu",
                "tudorandrei vilceanu",
                "vilceanu tudorandrei",
                "vilceanu tudor-andrei",
                "Tudor-Andrei Vilceanu",
                "Vilceanu Tudor-Andrei"));
        List<String> matchingNames = new ArrayList<>(List.of("tudorandei vilceanu"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
    }

    @Test
    public void nameMatchTestFuzzyCase8() {
        List<String> names = new ArrayList<>(Arrays.asList("dimitrioskonstantinostsogkas", "TSOGKASDIMITRIOS  KONSTANTINOS"));
        List<String> matchingNames = new ArrayList<>(Arrays.asList("dimiiriossogkas", "sogkasdimiirios"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
    }
}
