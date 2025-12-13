package com.backend.configuration.controller;

import com.backend.configuration.service.DataTypeService;
import com.backend.core.model.DataType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/config")
//@SecurityRequirement(name = "bearerAuth")
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;

    @PostMapping
    public ResponseEntity<?> addNewDT(@RequestBody @Valid DataType dataType) {
        try {
            DataType saved = dataTypeService.addNewDT(dataType);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DataType>> listAll() {
        return ResponseEntity.ok(dataTypeService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        Optional<DataType> opt = dataTypeService.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DataType not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody @Valid DataType dataType) {
        try {
            DataType updated = dataTypeService.update(id, dataType);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        dataTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
