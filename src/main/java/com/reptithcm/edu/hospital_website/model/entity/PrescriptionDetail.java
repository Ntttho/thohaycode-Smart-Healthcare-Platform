package com.reptithcm.edu.hospital_website.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_details")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    // Có thể thêm số lượng, liều dùng, số ngày sử dụng...
    @Column(length = 100)
    private String dosage; // Liều dùng (VD: 2 viên/ngày)

    private Integer quantity; // Số lượng
}