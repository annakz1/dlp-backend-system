package com.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  Used as policy objects to group Data Types for scan operations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSet {

    private UUID id;
    private UUID tenantId;
    private String name;
    private List<DataType> policy;

}
