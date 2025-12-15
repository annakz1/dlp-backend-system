package com.backend.policy.repo;

import com.backend.policy.model.DataSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataSetRepository extends JpaRepository<DataSetEntity, UUID> {

    List<DataSetEntity> findByTenantId(UUID tenantId);

}

