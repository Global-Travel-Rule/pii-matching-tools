import com.globaltravelrule.tools.matching.MatchingUtils;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;
import com.globaltravelrule.tools.matching.result.NameMatchingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Example {

    public static void main(String[] args) {
        float threshold = 0.9f;
        List<List<String>> names = new ArrayList<>(Arrays.asList(Collections.singletonList("Jon"), Collections.singletonList("Becky"), Collections.singletonList("Allen")));
        List<List<String>> matchingNames = new ArrayList<>(Arrays.asList(Collections.singletonList("Tom"), Collections.singletonList("Sean"), Collections.singletonList("Allen")));
        NameMatchingResult result = MatchingUtils.matchingNames(new NameMatchingOptions(names, matchingNames, threshold));
        result.printMatchingStackTrace();
        System.out.println("matching rate1:" + result.getMatchingRate() + " matching result1:" + result.getMatched());

        List<List<String>> names2 = new ArrayList<>(
                Arrays.asList(
                        Arrays.asList("tudor-andrei", "vilceanu"),
                        Arrays.asList("tudorandrei", "vilceanu"),
                        Arrays.asList("vilceanu", "tudorandrei"),
                        Arrays.asList("vilceanu", "tudor-andrei"),
                        Arrays.asList("Tudor-Andrei", "Vilceanu"),
                        Arrays.asList("Vilceanu", "Tudor-Andrei")));
        List<List<String>> matchingNames2 = new ArrayList<>(Collections.singletonList(Arrays.asList("tudorandei", "vilceanu")));
        NameMatchingResult result2 = MatchingUtils.matchingNames(new NameMatchingOptions(names2, matchingNames2, threshold));
        result2.printMatchingStackTrace();
        System.out.println("matching rate2:" + result.getMatchingRate() + " matching result2:" + result.getMatched());

        List<List<String>> names3 = new ArrayList<>(
                Arrays.asList(
                        Arrays.asList("tudor-andrei", "vilceanu"),
                        Arrays.asList("tudorandrei", "vilceanu"),
                        Arrays.asList("vilceanu", "tudorandrei"),
                        Arrays.asList("vilceanu", "tudor-andrei"),
                        Arrays.asList("Tudor-Andrei", "Vilceanu"),
                        Arrays.asList("Vilceanu", "Tudor-Andrei")));
        List<List<String>> matchingNames3 = new ArrayList<>(Arrays.asList(Collections.singletonList("tudorvilceanu"), Collections.singletonList("tudorvilceanu")));
        NameMatchingResult result3 = MatchingUtils.matchingNames(new NameMatchingOptions(names3, matchingNames3, threshold));
        result3.printMatchingStackTrace();
        System.out.println("matching rate3:" + result3.getMatchingRate() + " matching result3:" + result3.getMatched());

        List<List<String>> names4 = new ArrayList<>(Collections.singletonList(Arrays.asList("KIERON", "HALL")));
        List<List<String>> matchingNames4 = new ArrayList<>(Collections.singletonList(Arrays.asList("Kieron", "George", "Hall")));
        NameMatchingResult result4 = MatchingUtils.matchingNames(new NameMatchingOptions(names4, matchingNames4, threshold));
        result4.printMatchingStackTrace();
        System.out.println("matching rate4:" + result4.getMatchingRate() + " matching result4:" + result4.getMatched());
    }
}