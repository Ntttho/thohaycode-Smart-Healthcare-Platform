package com.reptithcm.edu.hospital_website.controller.doctor;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.medical.MedicalRecordRequest;
import com.reptithcm.edu.hospital_website.model.entity.MedicalRecord;
import com.reptithcm.edu.hospital_website.service.medical.MedicalRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.boot.internal.Abstract;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor/medical-record/{appointmentId}")
@AllArgsConstructor
public class DoctorMedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @GetMapping
    public String getViewMedicalRecord(
            HttpServletRequest request, @PathVariable("appointmentId") Long appointmentId,
            Model model
    ){
        model.addAttribute("medicalRecord", new MedicalRecordRequest());
        model.addAttribute("appointmentId", appointmentId);
        return "doctor/medical-record";
    }

    @PostMapping
    public String addMedicalRecord(
            HttpServletRequest request, @PathVariable("appointmentId") Long appointmentId,
            Model model, @Valid @ModelAttribute("medicalRecord")MedicalRecordRequest medicalRecordRequest
            ){
        try {
            medicalRecordService.addMedicalRecord(appointmentId, medicalRecordRequest);
            return "redirect:/doctor/prescriptions/" + appointmentId;
        } catch (AppException appException){
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }
}
