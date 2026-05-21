package com.reptithcm.edu.hospital_website.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("public")
public class ViewController {
    @GetMapping("introduce")
    public String getIntro(){
        return "public/introduce";
    }
}
