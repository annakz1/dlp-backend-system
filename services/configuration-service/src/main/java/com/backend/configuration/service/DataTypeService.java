package com.backend.configuration.service;

import com.backend.configuration.model.DataTypeEntity;
import com.backend.configuration.repo.DataTypeRepository;
import com.backend.core.model.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataTypeService {

    @Autowired
    private DataTypeRepository dataTypeRepository;

    public DataType addNewDT(DataType dto) {
        if (dto == null) throw new IllegalArgumentException("DataType cannot be null");
         // simple uniqueness check by name
        if (dto.getName() != null && dataTypeRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("DataType with the same name already exists");
        }
        DataTypeEntity entity = toEntity(dto);
        DataTypeEntity saved = dataTypeRepository.save(entity);
        return toDto(saved);
    }

    public List<DataType> listAll() {
        return dataTypeRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<DataType> findById(UUID id) {
        return dataTypeRepository.findById(id).map(this::toDto);
    }

    @Transactional
    public DataType update(UUID id, DataType dto) {
        DataTypeEntity entity = dataTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("DataType not found"));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setThreshold(dto.getThreshold());
        entity.setType(DataTypeEntity.Type.KEYWORDS);
        DataTypeEntity saved = dataTypeRepository.save(entity);
        return toDto(saved);
    }

    public void delete(UUID id) {
        dataTypeRepository.deleteById(id);
    }

    // Mapping helpers
    private DataType toDto(DataTypeEntity e) {
        DataType dto = new DataType();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setType(DataType.Type.KEYWORDS);
        dto.setContent(e.getContent());
        dto.setThreshold(e.getThreshold());
        return dto;
    }

    private DataTypeEntity toEntity(DataType dto) {
        DataTypeEntity e = new DataTypeEntity();
//        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setType(DataTypeEntity.Type.KEYWORDS);
        e.setContent(dto.getContent());
        e.setThreshold(dto.getThreshold());
        return e;
    }
}
