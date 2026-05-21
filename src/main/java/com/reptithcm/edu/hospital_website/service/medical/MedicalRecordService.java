package com.reptithcm.edu.hospital_website.service.medical;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.medical.MedicalRecordRequest;
import com.reptithcm.edu.hospital_website.model.entity.Appointment;
import com.reptithcm.edu.hospital_website.model.entity.MedicalRecord;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.AppointmentRepository;
import com.reptithcm.edu.hospital_website.repository.MedicalRecordRepository;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicalRecordService {
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    public void addMedicalRecord(Long appointmentId, MedicalRecordRequest medicalRecordRequest){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));
        MedicalRecord medicalRecord = medicalRecordRepository.findByAppointmentId(appointmentId).orElseGet(MedicalRecord::new);

        medicalRecord.setAppointment(appointment);
        medicalRecord.setDiagnosis(medicalRecordRequest.getDiagnosis());
        medicalRecord.setSymptoms(medicalRecordRequest.getSymptoms());

        medicalRecordRepository.save(medicalRecord);
    }
}
