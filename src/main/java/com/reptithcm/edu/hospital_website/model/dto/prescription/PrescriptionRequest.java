package com.reptithcm.edu.hospital_website.model.dto.prescription;

import com.reptithcm.edu.hospital_website.model.entity.Prescription;
import com.reptithcm.edu.hospital_website.model.entity.PrescriptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PrescriptionRequest {
    private Prescription prescription;
    private List<PrescriptionDetail> prescriptionDetails;
}
