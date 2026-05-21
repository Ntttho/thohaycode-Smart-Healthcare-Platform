package com.reptithcm.edu.hospital_website.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(nullable = false) // pending || accepted || rejected
    private String status = "pending";

    // Khi lưu Prescription, sẽ lưu luôn chi tiết đơn thuốc. Khi xóa đơn thuốc, xóa luôn chi tiết (orphanRemoval)
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();

    // Helper method để đồng bộ hóa quan hệ hai chiều
    public void addDetail(PrescriptionDetail detail) {
        prescriptionDetails.add(detail);
        detail.setPrescription(this);
    }

    public void removeDetail(PrescriptionDetail detail) {
        prescriptionDetails.remove(detail);
        detail.setPrescription(null);
    }
}