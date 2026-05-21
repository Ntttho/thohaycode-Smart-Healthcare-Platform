package com.reptithcm.edu.hospital_website.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginException extends RuntimeException{
    private String message;
}
