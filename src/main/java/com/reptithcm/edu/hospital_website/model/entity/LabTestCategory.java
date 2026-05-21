package com.reptithcm.edu.hospital_website.model.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Entity
@Table(name = "lab_test_categories")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LabTestCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}

