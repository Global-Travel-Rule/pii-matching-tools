package com.globaltravelrule.tools.matching.test;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.junit.Test;

public class PhoneticMatchTest {

    @Test
    public void testPhoneticFuzzyMatch() {
        String source = "ANLN SNM RPK";
        String target = "RPK SNM NNLN";

        System.out.println("Source phonetic: " + source);
        System.out.println("Target phonetic: " + target);
        System.out.println("tokenSortRatio: " + FuzzySearch.tokenSortRatio(source, target));
        System.out.println("tokenSetRatio: " + FuzzySearch.tokenSetRatio(source, target));
        System.out.println("ratio: " + FuzzySearch.ratio(source, target));
        System.out.println("partialRatio: " + FuzzySearch.partialRatio(source, target));
    }
}
