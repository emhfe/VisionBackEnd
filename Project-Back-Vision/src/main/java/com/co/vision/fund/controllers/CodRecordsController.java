package com.co.vision.fund.controllers;

import com.co.vision.fund.entity.CodRecords;
import com.co.vision.fund.services.CodRecordsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("visionfund/api/codrecords")
public class CodRecordsController {

    private final CodRecordsService service;

    @GetMapping("/get/all")
    public List<CodRecords> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/by/id/{id}")
    public ResponseEntity<CodRecords> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CodRecords create(@RequestBody CodRecords codProducto) {
        return service.create(codProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodRecords> update(@PathVariable Long id, @RequestBody CodRecords datos) {
        return service.update(id,datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
