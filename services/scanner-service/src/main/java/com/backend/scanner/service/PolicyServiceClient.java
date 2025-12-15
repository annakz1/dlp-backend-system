package com.backend.scanner.service;

import com.backend.core.model.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

/**
 * Simple client to call the Policy Service API. Uses RestTemplate and is intentionally
 * minimal — implemented here so the scanner-service can resolve DS policy via internal API.
 */
@Component
public class PolicyServiceClient {

    private static final Logger log = LoggerFactory.getLogger(PolicyServiceClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${policy.service.url:http://policy-service:8080}")
    private String baseUrl;

    public Optional<DataSet> getDataset(UUID id) {
        String url = baseUrl + "/api/policies/" + id;
        try {
            log.debug("Calling Policy Service GET {}", url);
            return Optional.ofNullable(restTemplate.getForObject(url, DataSet.class));
        } catch (HttpStatusCodeException ex) {
            String body = ex.getResponseBodyAsString();
            String msg = String.format("Failed to call Policy Service get: status=%s body=%s", ex.getStatusCode(), body);
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        } catch (RestClientException ex) {
            String msg = "Failed to call Policy Service get: " + ex.getMessage();
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }

}

