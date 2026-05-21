package com.reptithcm.edu.hospital_website.controller.patient;

import com.reptithcm.edu.hospital_website.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {
    private final DepartmentRepository departmentRepository;
    @GetMapping
    public String nav(){
        return "redirect:/patient/home";
    }

    @GetMapping("/home")
    public String getPatientHome(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "patient/home";
    }

}
