package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Long> {
}
