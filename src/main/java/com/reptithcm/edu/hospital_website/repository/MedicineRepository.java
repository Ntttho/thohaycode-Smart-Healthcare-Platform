package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.Medicine;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findAllByIsApproved(String isApproved);

    List<Medicine> getMedicinesByIsApproved(String isApproved);
}

