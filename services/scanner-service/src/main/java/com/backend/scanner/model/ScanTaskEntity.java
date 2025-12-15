package com.backend.scanner.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "scan_tasks", indexes = {
        @Index(name = "idx_scan_tasks_tenant", columnList = "tenant_id")
})
public class ScanTaskEntity {

    public enum Status {
        PENDING, RUNNING, COMPLETED, FAILED, CANCELLED
    }

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "ds_id", nullable = false)
    private UUID dsId;

    @Column(name = "input", columnDefinition = "text")
    private String input;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "error_message", length = 2000)
    private String errorMessage;

    public ScanTaskEntity() {
    }

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
    public UUID getDsId() { return dsId; }
    public void setDsId(UUID dsId) { this.dsId = dsId; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public Instant getFinishedAt() { return finishedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}

