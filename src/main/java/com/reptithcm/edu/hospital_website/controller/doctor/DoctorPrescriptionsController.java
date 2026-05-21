package com.reptithcm.edu.hospital_website.controller.doctor;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.prescription.PrescriptionRequest;
import com.reptithcm.edu.hospital_website.model.entity.Medicine;
import com.reptithcm.edu.hospital_website.model.entity.Prescription;
import com.reptithcm.edu.hospital_website.service.medical.MedicineService;
import com.reptithcm.edu.hospital_website.service.prescription.PrescriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/doctor/prescriptions")
public class DoctorPrescriptionsController {
    private final PrescriptionService prescriptionService;
    private final MedicineService medicineService;
    @GetMapping("/{appointmentId}")
    public String getViewPrescriptions(HttpServletRequest request, Model model, @PathVariable("appointmentId") String appointmentId){
        model.addAttribute("prescriptionRequest", new PrescriptionRequest());
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("medicine", medicineService.getMedicinesStatus("approved"));
        return "doctor/prescription";
    }

    @PostMapping("/{appointmentId}")
    public String addPrescriptionDetail(HttpServletRequest request, Model model,
                                        @ModelAttribute("prescriptionRequest") PrescriptionRequest prescriptionRequest,
                                        @ModelAttribute("medicine")List<Medicine> medicines, @PathVariable("appointmentId") Long appointmentId)
    {
        try {
            prescriptionService.prescribeMedication(Long.parseLong(AuthController.getId(request)), appointmentId, prescriptionRequest);
            return "redirect:/doctor/home";
        }catch (AppException e){
            model.addAttribute("message", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping
    public String getViewListPrescription(Model model, HttpServletRequest request){
        model.addAttribute("prescriptions", prescriptionService.getPrescriptions(Long.parseLong(AuthController.getId(request))));
        return "doctor/prescription-list";
    }


}
