package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndStartAt(Long doctorId, LocalDateTime startAt);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId " +
            "AND a.startAt < :endRequest " +
            "AND a.endAt > :startRequest"
    )
    boolean existsOverlap(@Param("doctorId") Long doctorId,
                          @Param("startRequest") LocalDateTime startRequest,
                          @Param("endRequest") LocalDateTime endRequest);

    List<Appointment> findAllByPatientId(Long patientId);

    List<Appointment> findAllByDoctorId(Long doctorId);
}
