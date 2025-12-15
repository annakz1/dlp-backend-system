package com.backend.configuration.service;

import com.backend.configuration.model.DataTypeEntity;
import com.backend.configuration.repo.DataTypeRepository;
import com.backend.core.model.DataType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataTypeServiceTest {

    @Mock
    private DataTypeRepository repo;

    @Mock
    private PolicyServiceClient policyClient;

    @InjectMocks
    private DataTypeService service;

    @Test
    void update_shouldSaveAndNotifyPolicyService() {
        UUID id = UUID.randomUUID();
        DataTypeEntity existing = getDataTypeEntity(id);

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        DataType dto = new DataType();
        dto.setName("new-name");
        dto.setDescription("new-desc");
        dto.setContent(java.util.List.of("p", "q"));
        dto.setThreshold(2.0);

        DataType result = service.update(id, dto);

        assertNotNull(result);
        assertEquals("new-name", result.getName());
        assertEquals("new-desc", result.getDescription());
        assertEquals(2.0, result.getThreshold());

        // verify repo.save called
        verify(repo, times(1)).save(any());

        // verify notify called with DTO containing same id
        ArgumentCaptor<DataType> captor = ArgumentCaptor.forClass(DataType.class);
        verify(policyClient, times(1)).notifyDataTypeUpdated(captor.capture());
        DataType notified = captor.getValue();
        assertEquals(id, notified.getId());
        assertEquals("new-name", notified.getName());
    }

    private static @NotNull DataTypeEntity getDataTypeEntity(UUID id) {
        DataTypeEntity existing = new DataTypeEntity();
        existing.setName("old");
        existing.setDescription("old desc");
        existing.setContent(java.util.List.of("a"));
        existing.setThreshold(1.0);
        // set id via reflection since field is private with no setter
        try {
            java.lang.reflect.Field idField = DataTypeEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existing, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return existing;
    }
}

