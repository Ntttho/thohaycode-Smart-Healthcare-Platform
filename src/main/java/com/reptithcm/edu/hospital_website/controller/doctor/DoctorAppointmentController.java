package com.reptithcm.edu.hospital_website.controller.doctor;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.appointment.AppointmentCompleteRequest;
import com.reptithcm.edu.hospital_website.service.appointment.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/waiting-list")
    public String getViewWaitingList(HttpServletRequest request, Model model) {
        String doctorId = AuthController.getId(request);
        model.addAttribute("appointments", appointmentService.getAppointmentWaiting(Long.parseLong(doctorId)));
        return "doctor/waiting-list";
    }

    @PostMapping("/waiting-list/confirm/{appointmentId}")
    public String confirmAppointment(@PathVariable("appointmentId") Long appointmentId,
                                     HttpServletRequest request,
                                     // Dùng @RequestParam và định dạng thời gian
                                     @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                     Model model
                                     ) {
        try {
            String doctorId = AuthController.getId(request);
            appointmentService.confirmAppointment(Long.parseLong(doctorId), appointmentId, endTime);
            return "redirect:/doctor/waiting-list";
        } catch (AppException appException) {
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }

    @PostMapping("/waiting-list/completed/{appointmentId}")
    public String completeAppointment(@PathVariable("appointmentId") Long appointmentId,
                                      @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                      HttpServletRequest request,
                                      Model model) {
        try {
            String doctorId = AuthController.getId(request);
            appointmentService.completeAppointment(Long.parseLong(doctorId), appointmentId, endTime);
            return "redirect:/doctor/medical-record/" + appointmentId; // chuyn trang chuẩn đoán bệnh
        } catch (AppException appException) {
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/waiting-list/reject/{appointmentId}")
    public String rejectAppointment(@PathVariable("appointmentId") Long appointmentId,
                                    HttpServletRequest request,
                                    Model model) {
        try {
            String doctorId = AuthController.getId(request);
            appointmentService.rejectAppointment(Long.parseLong(doctorId), appointmentId);
            return "redirect:/doctor/waiting-list";
        } catch (AppException appException) {
            model.addAttribute("message", appException.getMessage());
            return "exception/exception";
        }
    }
}