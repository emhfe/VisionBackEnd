package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.CodRecordsRepository;
import com.co.vision.fund.Repository.RecordsRepository;
import com.co.vision.fund.entity.CodRecords;
import com.co.vision.fund.entity.Records;
import com.co.vision.fund.services.FileMetadataService;
import com.co.vision.fund.services.RecordsService;
import jakarta.transaction.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordsServiceImpl implements RecordsService {
    private final RecordsRepository repository;

    private final CodRecordsRepository codRecordsRepository;

    private final FileMetadataService fileMetadataService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public List<Records> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Records> getByRecord(final Long codId, Pageable pageable, Long recordId) {

        Specification<Records> spec = Specification.where((root,
                                                           query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("codRecords").get("id"), codId)
        );

        if (recordId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("id"), recordId));
        }

        return repository.findAll(spec, pageable);
    }

    @Override
    public ResponseEntity<Records> getById(final Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Records create(final Records record) {

        Records result = repository.save(record);

        CodRecords codRecords = codRecordsRepository.findById(result.getCodRecords().getId()).get();

        String body = "Registration was made with ID: " + result.getId()
                + " in page: " + codRecords.getName() + " by the client with email: "
                + result.getEmail();
        String affair = "Registration was made with ID: " + result.getId();

        sendEmail(body, affair, "info@visionfundlu.com");

        return result;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> delete(final Long id) {
        if (repository.existsById(id)) {
            Records record = repository.findById(id).get();

            repository.deleteById(id);

            if(record.getKycId() != null) {
                fileMetadataService.deleteFile(record.getKycId().getId());
            }

            if(record.getProofAddress() != null) {
                fileMetadataService.deleteFile(record.getProofAddress().getId());
            }

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public void sendEmail(String body, String affair, String emailTo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailTo);
        mensaje.setSubject(affair);
        mensaje.setText(body);
        mensaje.setFrom(email);

        mailSender.send(mensaje);
    }
}
