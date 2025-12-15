package com.backend.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Used to define what kind of content should be scanned for.
 *
 * <p>Fields:
 * <ul>
 *   <li>id - UUID (generated internally)</li>
 *   <li>name - short human-readable name (required)</li>
 *   <li>description - optional longer description</li>
 *   <li>type - data type kind (enum). Current supported value: {@link Type#KEYWORDS}</li>
 *   <li>content - payload that describes the type behavior. For KEYWORDS this is a list
 *       of literal keyword phrases to match (at least one element is required)</li>
 *   <li>threshold - numeric threshold that controls how many matches are required
 *       for this DataType to be considered matched (defaults to 0.0)</li>
 * </ul>

 */
public class DataType {

    /** Supported DataType kinds. Currently only KEYWORDS is implemented. */
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

    // Constructors
    public DataType() {
        // defaults
        this.type = Type.KEYWORDS;
        this.content = new ArrayList<>();
        this.threshold = 0.0;
    }

    /**
     * Convenience constructor for tests and code that builds models programmatically.
     */
    public DataType(UUID id, String name, String description, Type type, List<String> content, Double threshold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type == null ? Type.KEYWORDS : type;
        this.content = content == null ? new ArrayList<>() : new ArrayList<>(content);
        this.threshold = threshold == null ? 0.0 : threshold;
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
