package com.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 *  Used to define what kind of content should be scanned for.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataType {

    public enum Type {
        KEYWORDS
    }

    private UUID id;
    private String name;
    private String description;
    private Type type;
    private List<String> content;
    private Double threshold;

}
