package com.co.vision.fund.Repository;

import com.co.vision.fund.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecordsRepository extends JpaRepository<Records, Long>, JpaSpecificationExecutor<Records> {
}
