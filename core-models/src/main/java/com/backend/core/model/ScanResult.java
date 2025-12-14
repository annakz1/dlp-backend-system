package com.backend.core.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Represents the result of running a scan for a tenant.
 * Stored when a scan is triggered.
 *
 * <p>Fields:
 * <ul>
 *   <li>{@link #id} - UUID (generated internally)</li>
 *   <li>{@link #tenantId} - UUID (tenant where the scan was executed; required)</li>
 *   <li>{@link #rawScanResult} - String (formatted/raw result produced by scanning process; required)</li>
 *   <li>{@link #scanPrediction} - ENUM (MATCH | NOT_MATCHED) indicating whether any policy matched</li>
 *   <li>{@link #foundDataTypes} - List&lt;UUID&gt; of DataType ids that matched in the scan (may be empty)</li>
 * </ul>
 */
public class ScanResult {

    public enum ScanPrediction {
        MATCH,
        NOT_MATCHED
    }

    private UUID id;

    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    @NotNull(message = "rawScanResult is required")
    private String rawScanResult;

    @NotNull(message = "scanPrediction is required")
    private ScanPrediction scanPrediction;

    @NotNull(message = "foundDataTypes is required (use empty list when none found)")
    @Size(min = 0)
    private List<UUID> foundDataTypes = new ArrayList<>();

    public ScanResult() {
        this.id = UUID.randomUUID();
    }

    public ScanResult(UUID id, UUID tenantId, String rawScanResult, ScanPrediction scanPrediction, List<UUID> foundDataTypes) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.tenantId = tenantId;
        this.rawScanResult = rawScanResult;
        this.scanPrediction = scanPrediction;
        this.foundDataTypes = foundDataTypes == null ? new ArrayList<>() : new ArrayList<>(foundDataTypes);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id == null ? UUID.randomUUID() : id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getRawScanResult() {
        return rawScanResult;
    }

    public void setRawScanResult(String rawScanResult) {
        this.rawScanResult = rawScanResult;
    }

    public ScanPrediction getScanPrediction() {
        return scanPrediction;
    }

    public void setScanPrediction(ScanPrediction scanPrediction) {
        this.scanPrediction = scanPrediction;
    }

    public List<UUID> getFoundDataTypes() {
        return foundDataTypes;
    }

    public void setFoundDataTypes(List<UUID> foundDataTypes) {
        this.foundDataTypes = foundDataTypes == null ? new ArrayList<>() : new ArrayList<>(foundDataTypes);
    }
}
