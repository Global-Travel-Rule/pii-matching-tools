/*
 * Copyright (c) 2022-2026 Global Travel Rule • globaltravelrule.com
 * License that can be found in the LICENSE file.
 * Author: Global Travel Rule developer
 * Created on: 2026/3/24 12:08
 */

package com.globaltravelrule.tools.matching.impl;

import com.globaltravelrule.tools.matching.api.NameProcessor;
import com.globaltravelrule.tools.matching.exceptions.MatchingException;
import com.globaltravelrule.tools.matching.options.NameMatchingOptions;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Name processor that removes common legal entity name variations and converts
 * full-width characters to half-width equivalents.
 *
 * <p>This processor runs AFTER the default {@link GlobalTravelRuleNameProcessor},
 * so input components are already lowercase, diacritics-stripped, and split on whitespace.
 * It joins components, applies longest-first entity term removal with word-boundary
 * matching, then splits back into components.</p>
 *
 * <p>Entity terms are loaded from the classpath resource {@code entity-name-terms.properties},
 * which is packaged inside the JAR. The standard {@link java.util.Properties} loader handles
 * Unicode escapes automatically. Terms are combined into a single alternation regex
 * pattern for efficient single-pass matching.</p>
 *
 * <p>Handles 12 categories of entity suffixes including: Private/Pte/Pvt, Limited/Ltd,
 * LLC, Inc, LP, Pty Ltd, Co, PLC, LLP, SP. Z O.O., SL, and all their variations.</p>
 *
 * @author Global Travel Rule developer
 * @version 1.2.0
 * @since 2025/10/20 14:00
 */
public class GlobalTravelRuleEntityNameVariationProcessor implements NameProcessor {

    private static final Logger log = LoggerFactory.getLogger(GlobalTravelRuleEntityNameVariationProcessor.class);

    private static final String TERMS_RESOURCE = "entity-name-terms.properties";

    /**
     * Single pre-compiled regex pattern combining all entity terms as alternations.
     * Uses word boundaries and case-insensitive matching. Terms are ordered longest-first
     * within the alternation to ensure greedy matching of multi-word phrases.
     */
    private static final Pattern ENTITY_PATTERN = buildEntityPattern();

    /**
     * Pre-compiled pattern for normalizing multiple whitespace characters into one space.
     */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /**
     * Load entity terms from the classpath properties file and build a single combined
     * alternation regex pattern for efficient single-pass matching.
     *
     * <p>Terms are sorted by length descending to ensure longest matches first
     * (e.g., "private limited" matches before "private" or "limited" alone).</p>
     *
     * @return compiled regex pattern combining all entity terms
     */
    private static Pattern buildEntityPattern() {
        List<String> terms = loadTermsFromResource();
        if (terms.isEmpty()) {
            log.warn("No entity terms loaded from {}; entity name variation removal is disabled", TERMS_RESOURCE);
            return Pattern.compile("(?!)");
        }

        // Sort by length descending for longest-first matching in alternation
        terms.sort((a, b) -> Integer.compare(b.length(), a.length()));

        // Build single alternation: \b(?:term1|term2|...)\b
        StringBuilder sb = new StringBuilder("\\b(?:");
        for (int i = 0; i < terms.size(); i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(Pattern.quote(terms.get(i)));
        }
        sb.append(")\\b");

        return Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    /**
     * Load entity terms from the classpath properties file using {@link java.util.Properties}.
     * Properties handles Unicode escapes natively. Values with keys matching
     * the prefix {@code term.} are collected as terms.
     *
     * @return list of terms (maybe empty if resource not found or unreadable)
     */
    private static List<String> loadTermsFromResource() {
        List<String> terms = new ArrayList<>();

        InputStream is = GlobalTravelRuleEntityNameVariationProcessor.class.getClassLoader()
                .getResourceAsStream(TERMS_RESOURCE);
        if (is == null) {
            log.warn("Classpath resource '{}' not found; falling back to empty term list", TERMS_RESOURCE);
            return terms;
        }

        try {
            Properties props = new Properties();
            props.load(is);

            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                if (value != null && !value.trim().isEmpty()) {
                    terms.add(value.trim());
                }
            }
        } catch (IOException e) {
            log.warn("Failed to read entity terms from '{}': {}", TERMS_RESOURCE, e.getMessage());
            throw new MatchingException(e);
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }

        return terms;
    }

    /**
     * Convert full-width ASCII characters (U+FF01 to U+FF5E) to their half-width equivalents.
     * Also converts the full-width space (U+3000) to a regular space.
     *
     * @param text the text to convert
     * @return text with full-width characters replaced by half-width equivalents
     */
    private String convertFullWidthToHalfWidth(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= '！' && c <= '～') {
                // Full-width ASCII variants: subtract 0xFEE0 to get half-width
                sb.append((char) (c - 0xFEE0));
            } else if (c == '\u3000') {
                // Full-width space -> regular space
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public List<String> processName(List<String> nameItems, NameMatchingOptions options) {
        if (nameItems == null || nameItems.isEmpty()) {
            return nameItems;
        }

        // Step 1: Full-width to half-width conversion on each component
        List<String> converted = new ArrayList<>();
        for (String item : nameItems) {
            if (item != null && !item.isEmpty()) {
                converted.add(convertFullWidthToHalfWidth(item));
            }
        }

        if (converted.isEmpty()) {
            return converted;
        }

        // Step 2: Join all components with space for multi-word pattern matching
        StringBuilder joined = new StringBuilder();
        for (int i = 0; i < converted.size(); i++) {
            if (i > 0) {
                joined.append(' ');
            }
            joined.append(converted.get(i));
        }
        String text = joined.toString();

        // Step 3: Single-pass entity term removal (all terms combined in one alternation pattern)
        text = ENTITY_PATTERN.matcher(text).replaceAll("");

        // Step 4: Normalize whitespace and split back into components
        text = WHITESPACE_PATTERN.matcher(text).replaceAll(" ").trim();
        if (text.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        for (String part : text.split(" ")) {
            if (!part.isEmpty()) {
                result.add(part);
            }
        }
        return result;
    }
}
