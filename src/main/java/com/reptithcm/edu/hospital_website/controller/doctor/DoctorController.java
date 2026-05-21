package com.reptithcm.edu.hospital_website.controller.doctor;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping
    public String getHome(HttpServletRequest request){
        if (!AuthController.checkDoctor(request)){
            return "redirect:/login";
        }
        return "redirect:/doctor/home";
    }

    @GetMapping("/home")
    public String getHomeDoctor(){
        return "doctor/home";
    }
}
