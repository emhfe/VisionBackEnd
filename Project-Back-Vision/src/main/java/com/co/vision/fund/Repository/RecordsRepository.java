package com.co.vision.fund.Repository;

import com.co.vision.fund.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RecordsRepository extends JpaRepository<Records, Long>, JpaSpecificationExecutor<Records> {
    Optional<Records> findByEmail(String email);

    boolean existsByEmail(String email);
}
