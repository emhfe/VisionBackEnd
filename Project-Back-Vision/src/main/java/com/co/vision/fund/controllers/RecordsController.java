package com.co.vision.fund.controllers;

import com.co.vision.fund.entity.Records;
import com.co.vision.fund.services.RecordsService;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("visionfund/api/records")
public class RecordsController {
    private final RecordsService service;

    @GetMapping("/get/all")
    public List<Records> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/by/cod/{codId}")
    public Page<Records> getByRecord(@PathVariable Long codId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(required = false) Long recordId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return service.getByRecord(codId, pageable, recordId);
    }

    @GetMapping("/get/by/id/{id}")
    public ResponseEntity<Records> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Records record) {
        try {
            Records nuevo = service.create(record);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
