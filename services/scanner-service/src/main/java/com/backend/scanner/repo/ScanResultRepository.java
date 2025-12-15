package com.backend.scanner.repo;

import com.backend.scanner.model.ScanResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScanResultRepository extends JpaRepository<ScanResultEntity, UUID> {
}

