package com.reptithcm.edu.hospital_website.model.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalRecordRequest {
    private String diagnosis;
    private String symptoms;
}
