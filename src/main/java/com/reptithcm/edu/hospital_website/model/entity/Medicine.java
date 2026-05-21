package com.reptithcm.edu.hospital_website.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private int amount;

    @Column(columnDefinition = "TEXT")
    private String precaution;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_approved", nullable = false)
    private String isApproved = "pending";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
