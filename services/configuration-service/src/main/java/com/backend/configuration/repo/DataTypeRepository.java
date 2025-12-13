package com.backend.configuration.repo;

import com.backend.configuration.model.DataTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataTypeRepository extends JpaRepository<DataTypeEntity, UUID> {

    Optional<DataTypeEntity> findByName(String name);

}

