package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.Prescription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @Query("SELECT p FROM Prescription p JOIN p.appointment a WHERE a.doctor.id = :doctorId")
    List<Prescription> getByDoctorId(@Param("doctorId") Long doctorId);

    @EntityGraph(attributePaths = {
            "prescriptionDetails",
            "appointment",
            "appointment.patient",
            "appointment.medicalRecord"
    })
    @Query("SELECT p FROM Prescription p WHERE p.appointment.doctor.id = :doctorId")
    List<Prescription> getByDoctorIdWithDetails(@Param("doctorId") Long doctorId);


    @EntityGraph(attributePaths = {
            "prescriptionDetails",
            "appointment",
            "appointment.doctor",
            "appointment.medicalRecord"
    })
    @Query("SELECT p FROM Prescription p WHERE p.appointment.id = :appointmentId")
    Optional<Prescription> findByAppointmentIdWithDetails(@Param("appointmentId") Long appointmentId);

    @EntityGraph(attributePaths = {
            "prescriptionDetails",
            "appointment",
            "appointment.doctor",
            "appointment.patient",
            "appointment.medicalRecord"
    })
    @Query("SELECT p FROM Prescription p ORDER BY p.id DESC")
    List<Prescription> findAllWithDetailsForAdmin();
}
