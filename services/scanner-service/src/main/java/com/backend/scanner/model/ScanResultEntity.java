package com.backend.scanner.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "scan_results", indexes = {
        @Index(name = "idx_scan_results_tenant", columnList = "tenant_id")
})
public class ScanResultEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "raw_scan_result", columnDefinition = "text")
    private String rawScanResult;

    @Column(name = "scan_prediction")
    private String scanPrediction;

    // store found data type ids as JSON array string
    @Column(name = "found_data_types", columnDefinition = "text")
    private String foundDataTypes;

    @Column(name = "created_at")
    private Instant createdAt;

    public ScanResultEntity() {}

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public String getRawScanResult() { return rawScanResult; }
    public void setRawScanResult(String rawScanResult) { this.rawScanResult = rawScanResult; }
    public String getScanPrediction() { return scanPrediction; }
    public void setScanPrediction(String scanPrediction) { this.scanPrediction = scanPrediction; }
    public String getFoundDataTypes() { return foundDataTypes; }
    public void setFoundDataTypes(String foundDataTypes) { this.foundDataTypes = foundDataTypes; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}

