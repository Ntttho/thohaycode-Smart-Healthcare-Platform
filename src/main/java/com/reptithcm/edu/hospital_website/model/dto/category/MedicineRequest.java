package com.reptithcm.edu.hospital_website.model.dto.category;

import com.reptithcm.edu.hospital_website.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequest {
    private Long id;
    @NotBlank(message = "Tên thuốc không được để trống")
    private String name;
    private int amount;
    private String precaution;
    private String description;
    private String isApproved;
//    @NotBlank(message = "Danh mục phải có")
    private Long categoryId;
}
