package com.reptithcm.edu.hospital_website.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Một hồ sơ bệnh án thuộc về một cuộc hẹn khám
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnosis; // Chuẩn đoán

    @Column(columnDefinition = "TEXT")
    private String symptoms; // Triệu chứng

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}