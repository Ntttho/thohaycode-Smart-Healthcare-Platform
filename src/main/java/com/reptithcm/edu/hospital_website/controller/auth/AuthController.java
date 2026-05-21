package com.reptithcm.edu.hospital_website.controller.auth;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.user.UserDTOResponse;
import com.reptithcm.edu.hospital_website.model.dto.user.UserLoginRequest;
import com.reptithcm.edu.hospital_website.model.dto.user.UserRegisterRequest;
import com.reptithcm.edu.hospital_website.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginView(Model model, HttpServletRequest request){

        String target = nav(request);
        if (target != null){
            return target;
        }

        model.addAttribute("request", new UserLoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String userLogin(@Valid @ModelAttribute("request") UserLoginRequest request,
                            BindingResult result,
                            HttpServletResponse response,
                            Model model){
        try{
            if (result.hasErrors()){
                return "auth/login";
            }

            UserDTOResponse user = authService.userLogin(request.getEmail(), request.getPassword());

            Cookie userCookie = new Cookie("USER_ID", user.getId().toString());
            Cookie roleUserCookie = new Cookie("ROLE_USER", user.getRole());
            userCookie.setMaxAge(7 * 24 * 60 * 60);
            userCookie.setPath("/");
            userCookie.setHttpOnly(true);
            response.addCookie(userCookie);
            response.addCookie(roleUserCookie);

            return "redirect:/" + user.getRole();
        }catch(AppException appException){
            model.addAttribute("request", request);
            model.addAttribute("errorMessage", appException.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String getRegisterView(Model model){
        // Phải khớp với tên @ModelAttribute trong hàm POST
        model.addAttribute("request", new UserRegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String userRegister(@Valid @ModelAttribute("request") UserRegisterRequest request,
                               BindingResult result,
                               Model model){

        if (result.hasErrors()) {
            return "auth/register"; // Quay lại trang và hiển thị lỗi
        }

        try {
            authService.userRegister(request);
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("request", request);
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){

        Cookie userCookie = new Cookie("USER_ID", null);
        Cookie roleUserCookie = new Cookie("ROLE_USER", null);
        userCookie.setMaxAge(0);
        userCookie.setPath("/");
        roleUserCookie.setMaxAge(0);
        roleUserCookie.setPath("/");
        response.addCookie(userCookie);
        response.addCookie(roleUserCookie);

        return "redirect:/login";
    }

    // nav su dung truc tiep o trang login
    public static String nav(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String role = null;
            for (Cookie cookie : cookies) {
                if ("ROLE_USER".equals(cookie.getName())) {
                    role = cookie.getValue();
                }
            }
            if (role != null && !role.isEmpty()) {
                return "redirect:/" + role.toLowerCase();
            }
        }

        return null;
    }

    public static boolean checkAdmin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String role = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ROLE_USER".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }
        return role.equals("admin");
    }

    public static boolean checkDoctor(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String role = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ROLE_USER".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }
        return role.equals("doctor");
    }

    public static String getId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String id = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("USER_ID".equals(cookie.getName())) {
                    id = cookie.getValue();
                    break;
                }
            }
        }
        return id;
    }

    public static String getRole(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String role = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("USER_ROLE".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }
        return role;
    }
}