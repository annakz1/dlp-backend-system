package com.backend.configuration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class DataTypeEmbeddable {

    @Column(name = "dt_id")
    private UUID id;

    @Column(name = "dt_name")
    private String name;

    @Column(name = "dt_description", length = 2000)
    private String description;

    public DataTypeEmbeddable() {
    }

    public DataTypeEmbeddable(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

