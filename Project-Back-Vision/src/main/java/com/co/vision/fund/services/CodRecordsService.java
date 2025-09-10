package com.co.vision.fund.services;

import com.co.vision.fund.entity.CodRecords;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CodRecordsService {
    List<CodRecords> getAll();

    ResponseEntity<CodRecords> getById(Long id);

    CodRecords create(CodRecords codRecord);

    ResponseEntity<CodRecords> update(Long id, CodRecords data);

    ResponseEntity<Void> delete(Long id);
}
