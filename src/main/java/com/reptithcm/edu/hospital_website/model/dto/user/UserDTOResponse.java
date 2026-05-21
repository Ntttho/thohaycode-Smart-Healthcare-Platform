package com.reptithcm.edu.hospital_website.model.dto.user;

import com.reptithcm.edu.hospital_website.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private Long id;
    private String email;
    @NotBlank(message = "Tên không được để trống")
    private String fullName;
    private String phone;
    private String gender;
    private LocalDate dob;
    private String role;

    private String departmentName;

    public static UserDTOResponse parseFromUser(User user) {
        UserDTOResponseBuilder builder = UserDTOResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .gender(user.getGender())
                .dob(user.getDob())
                .role(user.getRole());

        if (user.getDepartment() != null) {
            builder.departmentName(user.getDepartment().getName());
        } else {
            builder.departmentName("N/A");
        }

        return builder.build();
    }
}
