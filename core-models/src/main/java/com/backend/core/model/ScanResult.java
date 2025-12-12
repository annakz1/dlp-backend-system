package com.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Represents the result of running a scan for a tenant.
 * Stored when a scan is triggered.
 *
 * <p>Fields:
 * <ul>
 *   <li>{@link #id} - unique identifier for the scan result</li>
 *   <li>{@link #tenantId} - identifier for the tenant associated with the scan</li>
 *   <li>{@link #rawScanResult} - raw data returned from the scanning process</li>
 *   <li>{@link #scanPrediction} - prediction outcome of the scan (MATCH or NOT_MATCHED)</li>
 *   <li>{@link #foundDataTypes} - list of data type IDs that were found during the scan</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResult {

    public enum ScanPrediction {
        MATCH,
        NOT_MATCHED
    }

    private UUID id;
    private UUID tenantId;
    private String rawScanResult;
    private ScanPrediction scanPrediction;
    private List<UUID> foundDataTypes = new ArrayList<>();

}

