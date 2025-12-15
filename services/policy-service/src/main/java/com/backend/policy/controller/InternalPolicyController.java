package com.backend.policy.controller;

import com.backend.core.model.DataType;
import com.backend.policy.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/policies")
public class InternalPolicyController {

    @Autowired
    private DataSetService dataSetService;

    @PostMapping("/datatype-updated")
    public ResponseEntity<?> dataTypeUpdated(@RequestBody DataType dataType) {
        int updated = dataSetService.handleDataTypeUpdated(dataType);
        return ResponseEntity.ok().body("updatedDatasets=" + updated);
    }

    @PostMapping("/datatype-deleted/{id}")
    public ResponseEntity<?> dataTypeDeleted(@PathVariable("id") UUID id) {
        int updated = dataSetService.handleDataTypeDeleted(id);
        return ResponseEntity.ok().body("updatedDatasets=" + updated);
    }
}

