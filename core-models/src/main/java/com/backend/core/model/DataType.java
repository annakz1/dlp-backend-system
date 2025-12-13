package com.backend.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *  Used to define what kind of content should be scanned for.
 */
public class DataType {

    public enum Type {
        KEYWORDS
    }

    private UUID id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private Type type;

    @NotNull(message = "content is required")
    @Size(min = 1, message = "content must contain at least one keyword")
    private List<String> content;

    private Double threshold;



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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type == null ? Type.KEYWORDS : type;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content == null ? new ArrayList<>() : new ArrayList<>(content);
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold == null ? 0.0 : threshold;
    }

    @Override
    public String toString() {
        return "DataType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", content=" + content +
                ", threshold=" + threshold +
                '}';
    }
}
