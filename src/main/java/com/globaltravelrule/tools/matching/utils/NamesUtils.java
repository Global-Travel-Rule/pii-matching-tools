/*
 * Copyright (c) 2022-2025 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2025/11/14 16:56
 */

package com.globaltravelrule.tools.matching.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Utility class for handling names and converting them to a string representation.
 * Enhanced with CJK (Chinese/Japanese/Korean) character handling
 * @author Global Travel Rule developer
 * @version 1.0.1
 * @since 2025/11/14 16:56
 *
 */
public class NamesUtils {

    private static final Logger log = LoggerFactory.getLogger(NamesUtils.class);

    public static String namesToString(Collection<String> names) {
        if (names == null || names.isEmpty()) {
            return "";
        }
        if (names.stream().allMatch( name -> name == null || name.isEmpty())) {
            return "";
        }
        if (names.stream().allMatch(NamesUtils::isLatinScript)) {
            return String.join(" ", names);
        } else {
            return String.join("", names);
        }
    }

    public static boolean hasChineseCharacter(String str) {
        try {
            return ZhConverterUtil.containsChinese(str);
        } catch (Exception ex) {
            log.error("check chinese character error", ex);
            return false;
        }
    }

    public static String simplifyChineseCharacters(String str) {
        try {
            return ZhConverterUtil.toSimple(str);
        } catch (Exception ex) {
            log.error("simplify chinese characters error", ex);
            return str;
        }
    }

    /**
     * Check if the string contains CJK (Chinese, Japanese, Korean) characters.
     * This includes:
     * - CJK Unified Ideographs (Chinese characters)
     * - Hangul Syllables (Korean)
     * - Hiragana and Katakana (Japanese)
     *
     * @param str the string to check
     * @return true if the string contains any CJK characters
     */
    public static boolean hasCJKCharacter(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.codePoints().anyMatch(NamesUtils::checkCJKCharacter);
    }

    /**
     * All letters are in Latin (numbers/punctuation do not affect)
     * @param s the string to check
     * @return true if all letters are in Latin
     * */
    public static boolean isLatinScript(String s) {
        if (s == null || s.isEmpty()) return false;
        for (int i = 0; i < s.length(); ) {
            int cp = s.codePointAt(i);
            i += Character.charCount(cp);
            if (Character.isLetter(cp)) {
                if (Character.UnicodeScript.of(cp) != Character.UnicodeScript.LATIN) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the string is predominantly CJK (more than 50% CJK characters).
     *
     * @param str the string to check
     * @return true if more than half of the characters are CJK
     */
    public static boolean isPredominantlyCJK(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        long totalChars = str.codePoints().filter(cp -> !Character.isWhitespace(cp)).count();
        if (totalChars == 0) {
            return false;
        }
        long cjkChars = str.codePoints().filter(NamesUtils::checkCJKCharacter).count();
        return cjkChars * 2 > totalChars;
    }

    private static boolean checkCJKCharacter(int cp) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(cp);
        return block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || block == Character.UnicodeBlock.HANGUL_SYLLABLES
                || block == Character.UnicodeBlock.HANGUL_JAMO
                || block == Character.UnicodeBlock.HIRAGANA
                || block == Character.UnicodeBlock.KATAKANA;
    }
}
