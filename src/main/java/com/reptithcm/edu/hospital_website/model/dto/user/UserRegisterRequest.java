package com.reptithcm.edu.hospital_website.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Password không đưược để trống")
    @Size(min = 6, message = "Password ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Tên không được để trống")
    private String fullName;

    private LocalDate dob;

    @Pattern(regexp = "Male|Female", message = "Hãy chọn nam hoặc nữ")
    private String gender;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải 10 ký tự")
    private String phone;


}