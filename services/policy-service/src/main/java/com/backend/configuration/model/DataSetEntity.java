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

    // store resolved DataType payloads (embedded) as part of the dataset policy
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dataset_policy", joinColumns = @JoinColumn(name = "dataset_id"))
    private List<DataTypeEmbeddable> policy = new ArrayList<DataTypeEmbeddable>();

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

    public List<DataTypeEmbeddable> getPolicy() {
        return policy;
    }

    public void setPolicy(List<DataTypeEmbeddable> policy) {
        this.policy = policy;
    }
}
