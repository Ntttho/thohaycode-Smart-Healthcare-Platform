package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
