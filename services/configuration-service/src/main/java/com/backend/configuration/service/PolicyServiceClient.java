package com.backend.configuration.service;

import com.backend.core.model.DataSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${policy.service.url:http://localhost:8081}")
    private String baseUrl;

    public DataSet createDataset(DataSet ds) {
        try {
            String url = baseUrl + "/api/policies";
            return restTemplate.postForObject(url, ds, DataSet.class);
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to call Policy Service create", ex);
        }
    }

    public DataSet updateDataset(UUID id, DataSet ds) {
        try {
            String url = baseUrl + "/api/policies/" + id;
            ResponseEntity<DataSet> resp = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(ds), DataSet.class);
            return resp.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to call Policy Service update", ex);
        }
    }

    public Optional<DataSet> getDataset(UUID id) {
        try {
            String url = baseUrl + "/api/policies/" + id;
            return Optional.ofNullable(restTemplate.getForObject(url, DataSet.class));
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to call Policy Service get", ex);
        }
    }

    public List<DataSet> listDatasetsByTenant(UUID tenantId) {
        try {
            String url = baseUrl + "/api/policies/tenant/" + tenantId;
            ResponseEntity<List<DataSet>> resp = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    });
            return resp.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to call Policy Service listByTenant", ex);
        }
    }

    public void deleteDataset(UUID id) {
        try {
            String url = baseUrl + "/api/policies/" + id;
            restTemplate.delete(url);
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to call Policy Service delete", ex);
        }
    }
}
