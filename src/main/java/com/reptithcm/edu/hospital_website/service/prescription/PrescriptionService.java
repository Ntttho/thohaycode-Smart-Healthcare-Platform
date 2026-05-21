package com.reptithcm.edu.hospital_website.service.prescription;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.prescription.PrescriptionRequest;
import com.reptithcm.edu.hospital_website.model.entity.*;
import com.reptithcm.edu.hospital_website.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    @Transactional
    public void prescribeMedication(Long doctorId, Long appointmentId, PrescriptionRequest request) {
        if (doctorId == null || appointmentId == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND); // Hoặc tạo ErrorCode riêng báo thiếu ID
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);

        if (request.getPrescription() != null) {
            prescription.setNote(request.getPrescription().getNote());
        }

        List<PrescriptionDetail> detailsRequest = request.getPrescriptionDetails();

        if (detailsRequest != null && !detailsRequest.isEmpty()) {
            for (PrescriptionDetail detail : detailsRequest) {

                if (detail.getMedicine() == null || detail.getMedicine().getId() == null) {
                    continue;
                }

                Medicine medicine = medicineRepository.findById(detail.getMedicine().getId())
                        .orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND));

                detail.setMedicine(medicine);
                prescription.addDetail(detail);
            }
        }

        prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptions(Long doctorId){
        // check doctor

        User user = userRepository.findById(doctorId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!user.getRole().equals("doctor")){
            throw new AppException(ErrorCode.FORRBIDEN);
        };
        List<Prescription> prescriptions = prescriptionRepository.getByDoctorIdWithDetails(doctorId);
        return prescriptions;
    }

    public Prescription getPrescriptionByAppointmentId(Long appointmentId) {
        return prescriptionRepository.findByAppointmentIdWithDetails(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));
    }

    public List<Prescription> getAllPrescriptionsForAdmin() {
        return prescriptionRepository.findAllWithDetailsForAdmin();
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc!"));

        if (("completed".equals(status) || "accepted".equals(status))
                && "pending".equals(prescription.getStatus())) {

            for (PrescriptionDetail detail : prescription.getPrescriptionDetails()) {
                Medicine medicine = detail.getMedicine();
                int requiredQuantity = detail.getQuantity();
                int currentAmount = medicine.getAmount();

                if (currentAmount < requiredQuantity) {
                    throw new RuntimeException("Thuốc [" + medicine.getName() + "] không đủ số lượng trong kho. " +
                            "(Cần: " + requiredQuantity + ", Còn lại: " + currentAmount + ")");
                }

                medicine.setAmount(currentAmount - requiredQuantity);
                medicineRepository.save(medicine);
            }
        }

        prescription.setStatus(status);
        prescriptionRepository.save(prescription);
    }


}
