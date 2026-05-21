package com.reptithcm.edu.hospital_website.controller.admin;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.category.CategoryRequest;
import com.reptithcm.edu.hospital_website.model.entity.Category;
import com.reptithcm.edu.hospital_website.service.category.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public String getViewCategory(Model model, HttpServletRequest request){
        try {
            if (!AuthController.checkAdmin(request)) {
                return "redirect:/login";
            }
            String id = AuthController.getId(request);

            model.addAttribute("categories", categoryService.getCategories(id));
            model.addAttribute("category", new CategoryRequest());

            return "admin/category";
        }
        catch (AppException appException){
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }

    @PostMapping("/category/save")
    public String addCategory(@Valid @ModelAttribute("category")CategoryRequest categoryRequest,
                              BindingResult result, Model model,
                              HttpServletRequest request){
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories(AuthController.getId(request)));
            return "admin/category";
        }
        try{
            String userId = AuthController.getId(request);
            if (categoryRequest.getId() == null || categoryRequest.getId().isEmpty()) {
                categoryService.addCategory(userId, categoryRequest);
            } else {
                categoryService.updateCategory(userId, categoryRequest);
            }
            return "redirect:/admin/category";
        }catch (AppException e){
            model.addAttribute("message", e.getMessage());
            return "exception/exception";
        }
    }


    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") String id, HttpServletRequest request) {
        categoryService.delete(AuthController.getId(request) ,Long.parseLong(id));
        return "redirect:/admin/category";
    }


}
