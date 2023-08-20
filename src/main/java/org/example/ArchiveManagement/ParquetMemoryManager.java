package org.example.ArchiveManagement;

import lombok.Data;
import org.apache.avro.generic.GenericRecord;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Data
public class ParquetMemoryManager {
    ConcurrentHashMap<Long, List<GenericRecord>> map = new ConcurrentHashMap<>();
    private int Batch;
}
