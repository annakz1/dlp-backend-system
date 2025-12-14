package com.backend.configuration.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "datasets")
public class DataSetEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private String name;

    // store referenced DataType ids as a collection of UUID strings
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dataset_policy_datatype_ids", joinColumns = @JoinColumn(name = "dataset_id"))
    @Column(name = "data_type_id")
    private List<UUID> policyDataTypeIds = new ArrayList<>();

    public DataSetEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<UUID> getPolicyDataTypeIds() {
        return policyDataTypeIds;
    }

    public void setPolicyDataTypeIds(List<UUID> policyDataTypeIds) {
        this.policyDataTypeIds = policyDataTypeIds;
    }
}

