package com.reptithcm.edu.hospital_website.service.appointment;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.appointment.AppointmentRequest;
import com.reptithcm.edu.hospital_website.model.entity.Appointment;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.AppointmentRepository;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createAppointment(Long userId, AppointmentRequest request){
        // check patient + doctor
        User patient = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        User doctor = userRepository.findById(request.getDoctorId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.TIME_IN_PAST);
        }

        boolean isDuplicate = appointmentRepository.existsOverlap(request.getDoctorId(),
                request.getStartTime(),
                request.getStartTime());

        if (isDuplicate){
            throw new AppException(ErrorCode.APPOINTMENT_EXISTED);
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setReasonForVisit(request.getReasonForVisit());
        appointment.setStartAt(request.getStartTime());
        appointment.setStatus("pending");

        appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointmentsHistory(Long userid){
        return appointmentRepository.findAllByPatientId(userid);
    }

    @Transactional // doctor
    public void confirmAppointment(Long userId, Long appointmentId, LocalDateTime endTime){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!user.getRole().equals("doctor")){
            throw new AppException(ErrorCode.FORRBIDEN);
        }

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));
        // khong cho confirm các cái ca khám khác nữa
        if(appointmentRepository.existsOverlap(appointment.getDoctor().getId(), appointment.getStartAt(), endTime)){
            throw new AppException(ErrorCode.TIME_INVALID);
        }
        if(endTime.isBefore(LocalDateTime.now()) || endTime.isBefore(appointment.getStartAt())){
            throw new AppException(ErrorCode.TIME_INVALID);
        }

        appointment.setEndAt(endTime);
        appointment.setStatus("confirmed");

        appointmentRepository.save(appointment);
    }

    @Transactional // doctor
    public void completeAppointment(Long userId, Long appointmentId, LocalDateTime endTime){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!user.getRole().equals("doctor")){
            throw new AppException(ErrorCode.FORRBIDEN);
        }
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));

        if(!appointment.getStartAt().isBefore(endTime)){
            throw new AppException(ErrorCode.TIME_INVALID);
        }
        appointment.setStatus("completed");
        appointment.setEndAt(endTime);
        appointmentRepository.save(appointment);

        // Lưu trạng thái khám thành công -> ghi chuẩn đoán -> ghi đơn thuốc
    }

    @Transactional
    public void rejectAppointment(Long userId, Long appointmentId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOTFOUND));
        appointment.setStatus("cancelled");
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentWaiting(Long doctorId){
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!doctor.getRole().equals("doctor")){
            throw new AppException(ErrorCode.FORRBIDEN);
        }
        return appointmentRepository.findAllByDoctorId(doctorId).stream().filter(
                ap -> !ap.getStatus().equals("completed")
        ).toList();
    }


    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn này!"));

        if (!"PENDING".equalsIgnoreCase(appointment.getStatus())) {
            throw new RuntimeException("Chỉ có thể hủy lịch hẹn đang ở trạng thái Chờ xác nhận!");
        }
        appointmentRepository.delete(appointment);
    }

}
