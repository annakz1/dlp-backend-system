package com.backend.scanner.controller;

import com.backend.core.model.ScanResult;
import com.backend.scanner.model.ScanTaskEntity;
import com.backend.scanner.service.ScannerService;
import com.backend.scanner.service.TriggerRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scans")
public class ScanController {

    @Autowired
    private ScannerService scannerService;

    @PostMapping
    public ResponseEntity<Map<String, UUID>> trigger(@RequestBody @Valid TriggerRequest req) {
        UUID scanId = scannerService.createTask(req.getTenantId(), req.getDsId(), req.getInput());
        URI location = URI.create("/api/scans/" + scanId);
        return ResponseEntity.accepted().location(location).body(Map.of("scanId", scanId));
    }

    @GetMapping("/{scanId}")
    public ResponseEntity<?> poll(@PathVariable("scanId") UUID scanId, @RequestParam UUID tenantId) {
        Optional<ScanResult> scanResult = scannerService.getResult(scanId, tenantId);
        if (scanResult.isPresent()) {
            return ResponseEntity.ok(scanResult.get());
        }

        // result not present -> check task status
        Optional<ScanTaskEntity> taskOpt = scannerService.getTask(scanId, tenantId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Scan task not found", "scanId", scanId));
        }

        ScanTaskEntity task = taskOpt.get();
        Map<String, Object> body = new HashMap<>();
        body.put("scanId", scanId);
        body.put("status", task.getStatus().name());
        return getResponseEntityWithTaskStatus(scanId, task, body);
    }

    private static ResponseEntity<? extends Map<String, ?>> getResponseEntityWithTaskStatus(UUID scanId, ScanTaskEntity task, Map<String, Object> body) {
        if (task.getCreatedAt() != null) body.put("createdAt", task.getCreatedAt().toString());
        if (task.getStartedAt() != null) body.put("startedAt", task.getStartedAt().toString());
        if (task.getFinishedAt() != null) body.put("finishedAt", task.getFinishedAt().toString());
        if (task.getStatus() == ScanTaskEntity.Status.PENDING || task.getStatus() == ScanTaskEntity.Status.RUNNING) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(body);
        } else if (task.getStatus() == ScanTaskEntity.Status.FAILED) {
            body.put("error", task.getErrorMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        } else if (task.getStatus() == ScanTaskEntity.Status.CANCELLED) {
            return ResponseEntity.status(HttpStatus.GONE).body(body);
        } else {
            // completed but no ScanResult found -> treat as not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Scan completed but result missing", "scanId", scanId));
        }
    }
}
