package com.backend.scanner.repo;

import com.backend.scanner.model.ScanTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScanTaskRepository extends JpaRepository<ScanTaskEntity, UUID> {
    List<ScanTaskEntity> findByTenantId(UUID tenantId);
}

