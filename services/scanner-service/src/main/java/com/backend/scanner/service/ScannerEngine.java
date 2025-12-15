package com.backend.scanner.service;

import com.backend.core.model.DataSet;
import com.backend.scanner.model.ScanMatchResult;

public interface ScannerEngine {
    /**
     * Evaluate input against the dataset policy.
     * @param input text input to scan
     * @param ds dataset containing policy (list of DataType)
     * @return ScanMatchResult describing whether matched and detected objects
     */
    ScanMatchResult match(String input, DataSet ds);
}
