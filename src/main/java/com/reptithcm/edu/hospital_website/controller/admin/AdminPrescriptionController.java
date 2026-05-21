package com.reptithcm.edu.hospital_website.controller.admin;


import com.reptithcm.edu.hospital_website.service.prescription.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/prescription")
public class AdminPrescriptionController {

    private final PrescriptionService prescriptionService;

    // Hiển thị danh sách Đơn thuốc
    @GetMapping
    public String getViewPrescriptionAdmin(Model model){
        model.addAttribute("prescriptions", prescriptionService.getAllPrescriptionsForAdmin());
        return "admin/prescriptions";
    }

    @PostMapping("/update-status/{id}")
    public String updatePrescriptionStatus(@PathVariable("id") Long id,
                                           @RequestParam("status") String status) {
        prescriptionService.updateStatus(id, status);
        return "redirect:/admin/prescription";
    }


}
