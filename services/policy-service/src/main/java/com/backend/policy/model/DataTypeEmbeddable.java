package com.backend.policy.model;

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

    // store type.name() (e.g. KEYWORDS)
    @Column(name = "dt_type")
    private String type;

    // store content as JSON array string
    @Column(name = "dt_content", length = 4000)
    private String content;

    @Column(name = "dt_threshold")
    private Double threshold;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}
