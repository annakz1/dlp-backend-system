package com.backend.scanner.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TriggerRequest {

    private String input;

    @NotNull(message = "tenant_id is required")
    @JsonProperty("tenant_id")
    private UUID tenantId;

    @NotNull(message = "ds_id is required")
    @JsonProperty("ds_id")
    private UUID dsId;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getDsId() {
        return dsId;
    }

    public void setDsId(UUID dsId) {
        this.dsId = dsId;
    }
}

