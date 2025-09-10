package com.co.vision.fund.services;

import com.co.vision.fund.entity.Records;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RecordsService {
    List<Records> getAll();

    Page<Records> getByRecord(Long recordId, Pageable pageable, Long codId);

    ResponseEntity<Records> getById(Long id);

    Records create(Records record);

    ResponseEntity<Void> delete(Long id);
}
