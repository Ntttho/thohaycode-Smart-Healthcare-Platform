package com.reptithcm.edu.hospital_website.model.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private String id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    private String description;
}
