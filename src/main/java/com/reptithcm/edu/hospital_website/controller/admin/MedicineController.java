package com.reptithcm.edu.hospital_website.controller.admin;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.category.MedicineRequest;
import com.reptithcm.edu.hospital_website.model.entity.Medicine;
import com.reptithcm.edu.hospital_website.service.category.CategoryService;
import com.reptithcm.edu.hospital_website.service.medical.MedicineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/medicine")
@AllArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    private final CategoryService categoryService;

    @GetMapping
    public String getMedicines(HttpServletRequest request, Model model, @RequestParam(name = "status", required = false) String status){
        try {
            String userId = AuthController.getId(request);
            model.addAttribute("medicineRequest", new MedicineRequest());
            if(status == null){
                status = "approved";
            }
            List<Medicine> medicines = medicineService.getMedicinesStatus(status);
            model.addAttribute("medicines", medicines);

            model.addAttribute("categories", categoryService.getCategories(userId));
            return "admin/medicine";
        }catch (AppException appException){
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }

    @PostMapping("/save")
    public String saveMedicine(@Valid @ModelAttribute("medicineRequest")MedicineRequest medicineRequest, Model model,
                               BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return "admin/medicine";
        }
        try{
            String userId = AuthController.getId(request);
            medicineService.saveMedicine(userId, medicineRequest);
            return "redirect:/admin/medicine";
        }catch (AppException e){
            model.addAttribute("message", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable("id") String id, HttpServletRequest request){
        medicineService.rejectMedicine(AuthController.getId(request), id);
        return "redirect:/admin/medicine";
    }
}
