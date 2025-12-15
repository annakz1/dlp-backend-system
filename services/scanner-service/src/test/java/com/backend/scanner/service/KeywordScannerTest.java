package com.backend.scanner.service;

import com.backend.core.model.DataSet;
import com.backend.core.model.DataType;
import com.backend.scanner.model.ScanMatchResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class KeywordScannerTest {

    @Test
    void match_shouldReturnAllMatchingDataTypes() {
        KeywordScanner scanner = new KeywordScanner();

        DataType dt1 = new DataType();
        UUID id1 = UUID.randomUUID();
        dt1.setId(id1);
        dt1.setName("DT1");
        dt1.setContent(List.of("alpha", "beta"));
        dt1.setThreshold(1.0);

        DataType dt2 = new DataType();
        UUID id2 = UUID.randomUUID();
        dt2.setId(id2);
        dt2.setName("DT2");
        dt2.setContent(List.of("gamma"));
        dt2.setThreshold(1.0);

        DataSet ds = new DataSet();
        ds.setPolicy(List.of(dt1, dt2));

        String input = "This text contains alpha and gamma. Also alpha again.";

        ScanMatchResult res = scanner.match(input, ds);
        assertTrue(res.isMatched());
        assertEquals(2, res.getMatchedDataTypeIds().size());
        assertTrue(res.getMatchedDataTypeIds().contains(id1));
        assertTrue(res.getMatchedDataTypeIds().contains(id2));
        assertEquals(2, res.getDetectedObjects().size());

        // find DT1 object and assert match_count 2
        Map<String, Object> obj1 = res.getDetectedObjects().stream().filter(o -> id1.equals(o.get("id"))).findFirst().orElse(null);
        assertNotNull(obj1);
        assertEquals(2, ((Number)obj1.get("match_count")).intValue());
    }
}
