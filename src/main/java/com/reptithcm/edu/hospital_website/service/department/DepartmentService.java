package com.reptithcm.edu.hospital_website.service.department;

import com.reptithcm.edu.hospital_website.model.entity.Department;
import com.reptithcm.edu.hospital_website.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }
}
