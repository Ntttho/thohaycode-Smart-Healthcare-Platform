package com.reptithcm.edu.hospital_website.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("không tìm thấy người dùng"),
    WRONG_PASSWORD("Sai mật khẩu"),
    USER_EXISTED("Email này đã được đăng ký"),

    FORRBIDEN("Không có quyền hạn"), CATEGORY_NOT_FOUND("Không tìm thấy category"),
    MEDICINE_NOT_FOUND("Không tìm thấy thuốc"),
    TIME_IN_PAST("Thời gian trong quá khứ"),
    TIME_INVALID("Thời gian sai"), APPOINTMENT_EXISTED("Lịch khám này đã tồn tại"), APPOINTMENT_NOTFOUND("Appointment not found");

    public String message;


    ErrorCode(String message) {
        this.message = message;
    }
}