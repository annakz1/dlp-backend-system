package com.backend.scanner.service;

import com.backend.core.model.DataSet;
import com.backend.core.model.DataType;
import com.backend.scanner.model.ScanMatchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KeywordScanner implements ScannerEngine {

    private static final Logger logger = LoggerFactory.getLogger(KeywordScanner.class);

    @Override
    public ScanMatchResult match(String input, DataSet ds) {
        if (ds == null || ds.getPolicy() == null) {
            return new ScanMatchResult(false, Collections.emptyList(), Collections.emptyList());
        }

        // normalize input: trim and guard against null
        String normalized = (input == null) ? "" : input.trim();

        List<UUID> matched = new ArrayList<>();
        List<Map<String, Object>> detectedObjects = new ArrayList<>();

        for (DataType dt : ds.getPolicy()) {
            if (dt == null) continue;
            if (dt.getType() == DataType.Type.KEYWORDS) {
                int matchCount = 0;
                if (dt.getContent() != null) {
                    for (String keyword : dt.getContent()) {
                        if (keyword == null || keyword.isEmpty()) continue;
                        // build a regex that matches the keyword as a whole word/phrase (case-insensitive)
                        String regex = "\\b" + Pattern.quote(keyword) + "\\b";
                        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
                        Matcher m = p.matcher(normalized);
                        int occurrences = 0;
                        while (m.find()) occurrences++;
                        if (occurrences > 0) {
                            matchCount += occurrences;
                            logger.debug("Matched keyword '{}' in DT {} occurrences={}", keyword, dt.getId(), occurrences);
                        }
                    }
                }
                // threshold may be fractional; compare using double value
                double threshold = dt.getThreshold();
                if (matchCount >= threshold) {
                    matched.add(dt.getId());
                    Map<String, Object> detectedObject = new HashMap<>();
                    detectedObject.put("id", dt.getId());
                    detectedObject.put("name", dt.getName());
                    detectedObject.put("match_count", matchCount);
                    detectedObjects.add(detectedObject);
                    // continue collecting all matches
                }
            }
        }

        boolean anyMatched = !matched.isEmpty();
        return new ScanMatchResult(anyMatched, matched, detectedObjects);
    }
}
