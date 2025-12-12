package com.backend.configuration.controller;

import com.backend.configuration.service.ConfigurationService;
import com.backend.core.model.DataType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/config")
//@SecurityRequirement(name = "bearerAuth")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping
    public ResponseEntity<?> addNewDT(@RequestBody @Valid DataType movieDTO) {
        try {
            DataType dataType = configurationService.addNewDT(movieDTO);
            return new ResponseEntity<>(dataType, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
