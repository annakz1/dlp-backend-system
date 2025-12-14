package com.backend.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Used as policy objects to group Data Types for scan operations.
 *
 * <p>Fields:
 * <ul>
 *   <li>{@link #id} - UUID (generated internally)</li>
 *   <li>{@link #tenantId} - UUID (tenant owning this dataset, required)</li>
 *   <li>{@link #name} - String (dataset name, required)</li>
 *   <li>{@link #policy} - List&lt;DataType&gt; (list of DataType references that form the policy; must contain at least one)</li>
 * </ul>
 */
public class DataSet {

    private UUID id;

    @NotNull(message = "tenantId is required")
    private UUID tenantId;

    @NotBlank(message = "name is required")
    private String name;

    private List<DataType> policy;

    public DataSet() {
        this.id = UUID.randomUUID();
        this.policy = new ArrayList<>();
    }

    public DataSet(UUID id, UUID tenantId, String name, List<DataType> policy) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.tenantId = tenantId;
        this.name = name;
        this.policy = policy == null ? new ArrayList<>() : new ArrayList<>(policy);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataType> getPolicy() {
        return policy;
    }

    public void setPolicy(List<DataType> policy) {
        this.policy = policy == null ? new ArrayList<>() : new ArrayList<>(policy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSet)) return false;
        DataSet dataSet = (DataSet) o;
        return Objects.equals(id, dataSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", name='" + name + '\'' +
                ", policy=" + policy +
                '}';
    }
}
