package com.backend.policy.service;

import com.backend.core.model.DataSet;
import com.backend.core.model.DataType;
import com.backend.policy.model.DataSetEntity;
import com.backend.policy.repo.DataSetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataSetServiceTest {

    @Mock
    private DataSetRepository repo;

    @InjectMocks
    private DataSetService service;

    @Test
    void update_shouldSave() {
        UUID id = UUID.randomUUID();
        DataSetEntity existing = new DataSetEntity();
        existing.setName("old-name");
        existing.setTenantId(UUID.randomUUID());
        // set id via reflection
        try {
            java.lang.reflect.Field idField = DataSetEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(existing, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(repo.findById(id)).thenReturn(java.util.Optional.of(existing));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        DataSet dto = new DataSet();
        UUID tenant = UUID.randomUUID();
        dto.setTenantId(tenant);
        dto.setName("new-name");
        DataType dt = new DataType();
        UUID dtId = UUID.randomUUID();
        dt.setId(dtId);
        dt.setName("dt-name");
        dt.setContent(List.of("k1"));
        dt.setThreshold(1.0);
        dto.setPolicy(List.of(dt));

        DataSet updated = service.update(id, dto);

        assertNotNull(updated);
        assertEquals("new-name", updated.getName());
        assertEquals(tenant, updated.getTenantId());
        assertNotNull(updated.getPolicy());
        assertEquals(1, updated.getPolicy().size());
        verify(repo, times(1)).save(any(DataSetEntity.class));
    }
}
