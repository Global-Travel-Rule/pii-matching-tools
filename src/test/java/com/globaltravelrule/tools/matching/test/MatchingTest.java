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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MatchingTest {

    public static final float THRESHOLD = 0.8f;

    private final Logger log = LoggerFactory.getLogger(MatchingTest.class);

    @Test
    public void nameMatchTestNullCase1() {
        log.info("nameMatchTestNullCase1");
        List<String> matchName = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        List<List<String>> matchNames = new ArrayList<>();
        matchNames.add(matchName);
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(null, matchNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchNames, null, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNullCase2() {
        log.info("nameMatchTestNullCase2");
        List<String> name = new ArrayList<>(Arrays.asList("Jon", "Becky", "Allen"));
        List<List<String>> names = new ArrayList<>();
        names.add(name);
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, null, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(null, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNullCase3() {
        log.info("nameMatchTestNullCase3");
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(null, null, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(null, null, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestEmptyCase1() {
        log.info("nameMatchTestEmptyCase1");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("", "")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("", "")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCase1() {
        log.info("nameMatchTestNonChineseCase1");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("Jon"), Collections.singletonList("Becky"), Collections.singletonList("Allen")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Tom"), Collections.singletonList("Sean"), Collections.singletonList("Allen")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCase2() {
        log.info("nameMatchTestNonChineseCase2");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("Jon"), Collections.singletonList("Becky"), Collections.singletonList("Allen")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Tom"), Collections.singletonList("Bob")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCase3() {
        log.info("nameMatchTestNonChineseCase3");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList(""), Collections.singletonList("Becky"), Collections.singletonList("Tony")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Jon"), Collections.singletonList(""), Collections.singletonList("Allen")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestHasChineseCase1() {
        log.info("nameMatchTestHasChineseCase1");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("杨阳")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("杨阳")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestHasChineseCase2() {
        log.info("nameMatchTestHasChineseCase2");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("杨阳")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("楊陽")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestHasChineseCase3() {
        log.info("nameMatchTestHasChineseCase3");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("黄御鉴")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("黃禦鑑")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase1() {
        log.info("nameMatchTestNonChineseCharacterCase1");
        // Chinese traditional
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("欧応勲")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("歐應勳"), Collections.singletonList("欧应勋")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase2() {
        // Japanese
        log.info("nameMatchTestNonChineseCharacterCase2");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("渡辺図広")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("渡边图广"), Collections.singletonList("渡邊圖廣")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase3() {
        // Korean
        log.info("nameMatchTestNonChineseCharacterCase3");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("한성훈")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("韩成勋")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase4() {
        // Korean
        log.info("nameMatchTestNonChineseCharacterCase4");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("한성훈")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("한철희")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase5() {
        // Korean
        log.info("nameMatchTestNonChineseCharacterCase5");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("한선희")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("韩成勋"), Collections.singletonList("한선희")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase6() {
        // Arabic
        log.info("nameMatchTestNonChineseCharacterCase6");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("فهد عبدالعزيز الشمري")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Arrays.asList("Fahd", "Abdulaziz", "al-Shammari"), Collections.singletonList("فهد عبدالعزيز الشمري")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase7() {
        // Arabic
        log.info("nameMatchTestNonChineseCharacterCase7");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("فهد عبدالعزيز الشمري")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("محمد بن سلمان بن امین"), Arrays.asList("Muhammad", "bin", "Salman", "bin", "Amin")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase8() {
        // Russian
        log.info("nameMatchTestNonChineseCharacterCase8");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("Святополк")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Святополк"), Collections.singletonList("Sviatopolk")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase9() {
        // Russian
        log.info("nameMatchTestNonChineseCharacterCase9");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("Святополк")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Мстислав"), Collections.singletonList("Mstislav")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase10() {
        // Thai
        log.info("nameMatchTestNonChineseCharacterCase10");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("จารุณี")));
        List<String> matchingName = new ArrayList<>();
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("จารุณี"), Collections.singletonList("Jarunee")));
        matchingNames.add(matchingName);
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestNonChineseCharacterCase11() {
        // Thai
        log.info("nameMatchTestNonChineseCharacterCase11");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Collections.singletonList("จารุณี")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("จันจิรา"), Collections.singletonList("Janjira")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase1() {
        log.info("nameMatchTestFuzzyCase1");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("Bo MrsLi"), Collections.singletonList("小饼邱")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Collections.singletonList("boli")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase2() {
        log.info("nameMatchTestFuzzyCase2");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("vincenzopulera"), Collections.singletonList("pulera'vincenzo"), Collections.singletonList("PuleráVincenzo"), Collections.singletonList("pulerávincenzo"), Collections.singletonList("VincenzoPulerá"), Collections.singletonList("vincenzopulerá")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("vincenzopulerà"), Collections.singletonList("vincenzopulerà")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase3() {
        log.info("nameMatchTestFuzzyCase3");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("simonesilviacooperforster"), Collections.singletonList("pulera'cooperforstersimonesilvia"), Collections.singletonList("coopersimone"), Collections.singletonList("CooperSimone"), Collections.singletonList("simonecooper"), Collections.singletonList("SimoneCooper")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("simonesilviacooper"), Collections.singletonList("coopersimonesilvia")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase4() {
        // not match
        log.info("nameMatchTestFuzzyCase4");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("litzuhui"), Arrays.asList("LiTzu", "Hui"), Collections.singletonList("tzuhuili"), Arrays.asList("Tzu", "HuiLi")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("tzu-huili"), Collections.singletonList("litzu-hui")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase5() {
        // not match 0.8
        log.info("nameMatchTestFuzzyCase5");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("bùiđuccanh"), Collections.singletonList("pulera'canhbuiduc"), Collections.singletonList("CANHBUIDUC"), Collections.singletonList("buiduccanh"), Collections.singletonList("BUIDUCCANH")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("buiduccanh"), Collections.singletonList("bùiđứccảnh"), Collections.singletonList("bùiđứccảnh"), Collections.singletonList("buiduccanh")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase6() {
        // not match 0.8
        log.info("nameMatchTestFuzzyCase6");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Arrays.asList("tudor-andrei", "vilceanu"), Arrays.asList("tudorandrei", "vilceanu"), Arrays.asList("vilceanu", "tudorandrei"), Arrays.asList("vilceanu", "tudor-andrei"), Arrays.asList("Tudor-Andrei", "Vilceanu"), Arrays.asList("Vilceanu", "Tudor-Andrei")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("tudorvilceanu"), Collections.singletonList("tudorvilceanu")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase7() {
        // not match 0.8
        log.info("nameMatchTestFuzzyCase7");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Arrays.asList("tudor-andrei", "vilceanu"), Arrays.asList("tudorandrei", "vilceanu"), Arrays.asList("vilceanu", "tudorandrei"), Arrays.asList("vilceanu", "tudor-andrei"), Arrays.asList("Tudor-Andrei", "Vilceanu"), Arrays.asList("Vilceanu", "Tudor-Andrei")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("tudorandei", "vilceanu")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase8() {
        log.info("nameMatchTestFuzzyCase8");
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("dimitrioskonstantinostsogkas"), Arrays.asList("TSOGKASDIMITRIOS", "KONSTANTINOS")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("dimiiriossogkas"), Collections.singletonList("sogkasdimiirios")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestFuzzyCase9() {
        log.info("nameMatchTestFuzzyCase9");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("COZMA", "CONSTANTIN-CIPRIAN")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Ciprian", "Cozma")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertFalse(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase1() {
        log.info("nameMatchTestPostCase1");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("KAMAL J MANGROLIYA", "")));
        List<List<String>> matchingNames = Arrays.asList(Arrays.asList("KAMAL", "JAYSUKHBHAI", "MANGROLIYA"), Arrays.asList("MANGROLIYA", "JAYSUKHBHAI"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase2() {
        log.info("nameMatchTestPostCase2");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("JAMES", "BAKER")));
        List<List<String>> matchingNames = Collections.singletonList(Arrays.asList("James", "Stuart", "Baker"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase3() {
        log.info("nameMatchTestPostCase3");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("SAIBALAJI E L", "")));
        List<List<String>> matchingNames = Arrays.asList(
                Arrays.asList("EMBULUR", "LOGESHBABU", "SAIBALAJI"),
                Arrays.asList("SAI BALAJI E", "L"));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase4() {
        log.info("nameMatchTestPostCase4");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("KIERON", "HALL")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Kieron", "George", "Hall")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase5() {
        log.info("nameMatchTestPostCase5");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("Olivia", "Maggs")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Olivia", "Michaela", "Maggs")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase6() {
        log.info("nameMatchTestPostCase6");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("SMYRGALA", "MAREK PIOTR")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Marek", "piotr", "Smyrgala")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase7() {
        log.info("nameMatchTestPostCase7");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("PIVA", "THIERRY JEAN LUC")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Thierry", "Jean-Luc", "Piva")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }

    @Test
    public void nameMatchTestPostCase8() {
        log.info("nameMatchTestPostCase8");
        List<List<String>> names = new ArrayList<>(Collections.singletonList(Arrays.asList("NAZYA", "HENARE")));
        List<List<String>> matchingNames = new ArrayList<>(Collections.singletonList(Arrays.asList("Nazya", "Ray-anne Tarianna", "Henare")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, THRESHOLD));
        result.printMatchingStackTrace();
        Assert.assertTrue(result.getMatched());
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(matchingNames, names, THRESHOLD));
        Assert.assertEquals(result.getMatchingRate(), result2.getMatchingRate());
    }
}
