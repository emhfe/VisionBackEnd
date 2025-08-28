package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.CodRecordsRepository;
import com.co.vision.fund.entity.CodRecords;
import com.co.vision.fund.services.CodRecordsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodRecordsServicesImpl implements CodRecordsService {

    private final CodRecordsRepository repository;

    @Override
    public List<CodRecords> getAll() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<CodRecords> getById(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public CodRecords create(final CodRecords codRecord) {
        return repository.save(codRecord);
    }

    @Override
    public ResponseEntity<CodRecords> update(final Long id, final CodRecords data) {
        return repository.findById(id)
            .map(codRecords -> {
                codRecords.setName(data.getName());
                return ResponseEntity.ok(repository.save(codRecords));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
