package com.reptithcm.edu.hospital_website.controller.admin;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getHome(HttpServletRequest request){
        if (!AuthController.checkAdmin(request)){
            return "redirect:/login";
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/home")
    public String getAdminHome(){
        return "admin/home";
    }

}
