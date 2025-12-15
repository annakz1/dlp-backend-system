package com.backend.scanner.service;

import com.backend.core.model.DataSet;
import com.backend.core.model.ScanResult;
import com.backend.scanner.model.ScanMatchResult;
import com.backend.scanner.model.ScanResultEntity;
import com.backend.scanner.model.ScanTaskEntity;
import com.backend.scanner.repo.ScanResultRepository;
import com.backend.scanner.repo.ScanTaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class ScannerService {

    private static final Logger logger = LoggerFactory.getLogger(ScannerService.class);

    @Autowired
    private ScanTaskRepository scanTaskRepository;

    @Autowired
    private ScanResultRepository scanResultRepository;

    @Autowired
    @Qualifier("scannerExecutor")
    private Executor scannerExecutor;

    @Autowired
    private ScannerEngine scannerEngine;

    @Autowired
    private PolicyServiceClient policyServiceClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Create task, run async job; persist ScanResult only once job completes
    public UUID createTask(UUID tenantId, UUID dsId, String input) {
        UUID scanId = UUID.randomUUID();

        ScanTaskEntity task = new ScanTaskEntity();
        task.setId(scanId);
        task.setTenantId(tenantId);
        task.setDsId(dsId);
        task.setInput(input);
        task.setStatus(ScanTaskEntity.Status.PENDING);
        task.setCreatedAt(Instant.now());
        scanTaskRepository.save(task);

        // No ScanResultEntity is saved here. The result will be created and saved
        // when the background job completes (success or failure).

        // Run async job
        CompletableFuture.runAsync(() -> runScanJob(scanId, tenantId, dsId, input), scannerExecutor)
                .exceptionally(ex -> {
                    logger.error("Scan job failed for {}", scanId, ex);
                    return null;
                });

        return scanId;
    }

    public Optional<ScanResult> getResult(UUID scanId, UUID tenantId) {
        Optional<ScanResultEntity> e = scanResultRepository.findById(scanId);
        if (e.isEmpty()) return Optional.empty();
        ScanResultEntity re = e.get();
        if (!re.getTenantId().equals(tenantId)) return Optional.empty();
        // if scan is not yet completed (rawScanResult == null) return empty to signal RUNNING
        if (re.getRawScanResult() == null) return Optional.empty();

        ScanResult r = new ScanResult();
        r.setId(re.getId());
        r.setTenantId(re.getTenantId());
        r.setRawScanResult(re.getRawScanResult());
        r.setScanPrediction(re.getScanPrediction() == null ? ScanResult.ScanPrediction.NOT_MATCHED : ScanResult.ScanPrediction.valueOf(re.getScanPrediction()));
        try {
            if (re.getFoundDataTypes() != null) {
                List<UUID> found = MAPPER.readValue(re.getFoundDataTypes(), MAPPER.getTypeFactory().constructCollectionType(List.class, UUID.class));
                r.setFoundDataTypes(found);
            }
        } catch (JsonProcessingException ex) {
            // ignore parse problems
        }
        return Optional.of(r);
    }

    // Expose task lookup so controllers can report status (PENDING, RUNNING, FAILED, etc.)
    public Optional<ScanTaskEntity> getTask(UUID scanId, UUID tenantId) {
        Optional<ScanTaskEntity> t = scanTaskRepository.findById(scanId);
        if (t.isEmpty()) return Optional.empty();
        if (!t.get().getTenantId().equals(tenantId)) return Optional.empty();
        return t;
    }

    private void runScanJob(UUID scanId, UUID tenantId, UUID dsId, String input) {
        Optional<ScanTaskEntity> ot = scanTaskRepository.findById(scanId);
        if (ot.isEmpty()) return;
        ScanTaskEntity task = ot.get();
        task.setStatus(ScanTaskEntity.Status.RUNNING);
        task.setStartedAt(Instant.now());
        scanTaskRepository.save(task);

        // Scanner pulls DS policy from Policy Service API and applies logic per DT
        try {
            // 1) load DS policy from Policy Service via client
            Optional<DataSet> ods = policyServiceClient.getDataset(dsId);
            if (ods.isEmpty()) throw new IllegalArgumentException("DataSet not found: " + dsId);
            DataSet ds = ods.get();

            // Delegate matching to the pluggable scanner engine
            ScanMatchResult matchResult = scannerEngine.match(input, ds);
            boolean scanMatch = matchResult != null && matchResult.isMatched();
            List<UUID> matched = matchResult != null ? matchResult.getMatchedDataTypeIds() : Collections.emptyList();
            List<Map<String, Object>> detectedObjects = matchResult != null ? matchResult.getDetectedObjects() : Collections.emptyList();

            // build formatted JSON result
            ScanResultEntity resultEntity = new ScanResultEntity();
            resultEntity.setId(scanId);
            resultEntity.setTenantId(tenantId);
            Map<String, Object> matchResultMap = new HashMap<>();
            try {
                if (scanMatch) {
                    matchResultMap.put("status", "match");
                    Map<String, Object> res = new HashMap<>();
                    res.put("detected_objects", detectedObjects);
                    matchResultMap.put("result", res);
                } else {
                    matchResultMap.put("status", "not matched");
                }
                resultEntity.setRawScanResult(MAPPER.writeValueAsString(matchResultMap));
            } catch (JsonProcessingException ex) {
                // fallback to plain string if serialization fails
                resultEntity.setRawScanResult(scanMatch ? "{\"status\":\"match\"}" : "{\"status\":\"not matched\"}");
            }

            resultEntity.setScanPrediction(scanMatch ? "MATCH" : "NOT_MATCHED");
            try {
                resultEntity.setFoundDataTypes(MAPPER.writeValueAsString(matched));
            } catch (JsonProcessingException ex) {
                resultEntity.setFoundDataTypes(null);
            }
            resultEntity.setCreatedAt(Instant.now());
            scanResultRepository.save(resultEntity);

            task.setStatus(ScanTaskEntity.Status.COMPLETED);
            task.setFinishedAt(Instant.now());
            scanTaskRepository.save(task);

        } catch (Exception ex) {
            logger.error("Error during scan job {}", scanId, ex);
            task.setStatus(ScanTaskEntity.Status.FAILED);
            task.setFinishedAt(Instant.now());
            task.setErrorMessage(ex.getMessage());
            scanTaskRepository.save(task);

            // update result entity with failure info
            ScanResultEntity re = new ScanResultEntity();
            re.setId(scanId);
            re.setTenantId(tenantId);
            re.setRawScanResult("Failed: " + ex.getMessage());
            re.setScanPrediction("NOT_MATCHED");
            re.setCreatedAt(Instant.now());
            scanResultRepository.save(re);
        }
    }
}
