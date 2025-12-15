package com.backend.scanner.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScanMatchResult {
    private final boolean matched;
    private final List<UUID> matchedDataTypeIds;
    private final List<Map<String, Object>> detectedObjects;

    public ScanMatchResult(boolean matched, List<UUID> matchedDataTypeIds, List<Map<String, Object>> detectedObjects) {
        this.matched = matched;
        this.matchedDataTypeIds = matchedDataTypeIds;
        this.detectedObjects = detectedObjects;
    }

    public boolean isMatched() {
        return matched;
    }

    public List<UUID> getMatchedDataTypeIds() {
        return matchedDataTypeIds;
    }

    public List<Map<String, Object>> getDetectedObjects() {
        return detectedObjects;
    }
}

