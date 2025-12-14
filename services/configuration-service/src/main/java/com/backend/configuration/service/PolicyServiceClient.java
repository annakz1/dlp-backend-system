package com.backend.configuration.service;

import com.backend.core.model.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Client to communicate with the Policy Service. Uses RestTemplate for simplicity.
 * The Policy Service URL is configurable via 'policy.service.url'.
 */
@Component
public class PolicyServiceClient {

    private static final Logger log = LoggerFactory.getLogger(PolicyServiceClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${policy.service.url:http://localhost:8081}")
    private String baseUrl;

    public DataSet createDataset(DataSet ds) {
        String url = baseUrl + "/api/policies";
        try {
            log.debug("Calling Policy Service CREATE {} payload={}", url, ds);
            return restTemplate.postForObject(url, ds, DataSet.class);
        } catch (HttpStatusCodeException ex) {
            String body = ex.getResponseBodyAsString();
            String msg = String.format("Failed to call Policy Service create: status=%s body=%s", ex.getStatusCode(), body);
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        } catch (RestClientException ex) {
            String msg = "Failed to call Policy Service create: " + ex.getMessage();
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }

    public DataSet updateDataset(UUID id, DataSet ds) {
        String url = baseUrl + "/api/policies/" + id;
        try {
            log.debug("Calling Policy Service UPDATE {} payload={}", url, ds);
            ResponseEntity<DataSet> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(ds), DataSet.class);
            return resp.getBody();
        } catch (HttpStatusCodeException ex) {
            String body = ex.getResponseBodyAsString();
            String msg = String.format("Failed to call Policy Service update: status=%s body=%s", ex.getStatusCode(), body);
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        } catch (RestClientException ex) {
            String msg = "Failed to call Policy Service update: " + ex.getMessage();
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }

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

    public List<DataSet> listDatasetsByTenant(UUID tenantId) {
        String url = baseUrl + "/api/policies/tenant/" + tenantId;
        try {
            log.debug("Calling Policy Service LIST {}", url);
            ResponseEntity<List<DataSet>> resp = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<DataSet>>() {
                    });
            return resp.getBody();
        } catch (HttpStatusCodeException ex) {
            String body = ex.getResponseBodyAsString();
            String msg = String.format("Failed to call Policy Service listByTenant: status=%s body=%s", ex.getStatusCode(), body);
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        } catch (RestClientException ex) {
            String msg = "Failed to call Policy Service listByTenant: " + ex.getMessage();
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }

    public void deleteDataset(UUID id) {
        String url = baseUrl + "/api/policies/" + id;
        try {
            log.debug("Calling Policy Service DELETE {}", url);
            restTemplate.delete(url);
        } catch (HttpStatusCodeException ex) {
            String body = ex.getResponseBodyAsString();
            String msg = String.format("Failed to call Policy Service delete: status=%s body=%s", ex.getStatusCode(), body);
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        } catch (RestClientException ex) {
            String msg = "Failed to call Policy Service delete: " + ex.getMessage();
            log.error(msg, ex);
            throw new RuntimeException(msg, ex);
        }
    }
}
