package com.reptithcm.edu.hospital_website.controller.profile;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.user.UserDTOResponse;
import com.reptithcm.edu.hospital_website.model.dto.user.UserUpdateProfileRequest;
import com.reptithcm.edu.hospital_website.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("profile")
public class ProfileController {
    private final UserService userService;
    @GetMapping
    public String getProfile(Model model, HttpServletRequest request){
        try {
            String id = AuthController.getId(request);
            UserDTOResponse userDTOResponse = userService.getProfile(id);
            model.addAttribute("user", new UserUpdateProfileRequest(
                    userDTOResponse.getEmail(),
                    userDTOResponse.getRole(),
                    userDTOResponse.getFullName(),
                    userDTOResponse.getPhone(),
                    userDTOResponse.getGender(),
                    userDTOResponse.getDob(),
                    userDTOResponse.getDepartmentName()
            ));
            return "util/profile";
        } catch (AppException appException){
            return "exception/exception";
        }
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute("user") UserUpdateProfileRequest user,
                                BindingResult result,
                                HttpServletRequest request) {
        if (result.hasErrors()) {
            return "util/profile";
        }
        try {
            String id = AuthController.getId(request);
            userService.updateProfile(id, user);
            return "redirect:/profile?success";
        } catch (AppException appException){
            return "redirect:/profile?error";
        }
    }
}
