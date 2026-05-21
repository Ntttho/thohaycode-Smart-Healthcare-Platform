package com.reptithcm.edu.hospital_website.controller.patient;

import com.reptithcm.edu.hospital_website.controller.auth.AuthController;
import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.model.dto.appointment.AppointmentRequest;
import com.reptithcm.edu.hospital_website.model.entity.Prescription;
import com.reptithcm.edu.hospital_website.service.appointment.AppointmentService;
import com.reptithcm.edu.hospital_website.service.department.DepartmentService;
import com.reptithcm.edu.hospital_website.service.prescription.PrescriptionService;
import com.reptithcm.edu.hospital_website.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientAppointmentController {
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final PrescriptionService prescriptionService;

    @GetMapping("/appointment/booking/{departmentId}")
    public String getViewBooking(Model model, @PathVariable(name = "departmentId")Long departmentId){
        model.addAttribute("appointment", new AppointmentRequest());
        model.addAttribute("doctors", userService.getDoctors(departmentId));
        model.addAttribute("departmentId", departmentId);
        return "patient/booking";
    }

    @PostMapping("/appointment/create/{departmentId}")
    public String createAppointment(@Valid @ModelAttribute("appointment")AppointmentRequest appointmentRequest,
                                    HttpServletRequest request,
                                    Model model,
                                    BindingResult result,
                                    @PathVariable(name = "departmentId") Long departmentId){
        if(result.hasErrors()){
            model.addAttribute("doctors", userService.getDoctors(departmentId));
            return "patient/booking";
        }
        try{
            String userId = AuthController.getId(request);
            appointmentService.createAppointment(Long.parseLong(userId), appointmentRequest);
            return "redirect:/patient/home";
        }catch (AppException e){
            model.addAttribute("message", e.getMessage());
            return "exception/exception";
        }
    }

    @GetMapping("/appointments")
    public String getViewHistory(Model model, HttpServletRequest request){
        String userId = AuthController.getId(request);
        model.addAttribute("appointments", appointmentService.getMyAppointmentsHistory(Long.parseLong(userId)));
        return "patient/history";
    }

    @GetMapping("/prescription/{appointmentId}")
    public String getViewAppointment(@PathVariable("appointmentId") Long appointmentId, HttpServletRequest request,
                                     Model model){
        Prescription prescription = prescriptionService.getPrescriptionByAppointmentId(appointmentId);

        // Đẩy dữ liệu ra View
        model.addAttribute("prescription", prescription);
        return "patient/prescription";
    }


    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Long id,
                                    HttpServletRequest request,
                                    Model model) {
        try {
            // 1. Thực hiện hủy lịch
            appointmentService.cancelAppointment(id);
            model.addAttribute("successMessage", "Đã hủy lịch hẹn thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        // 2. BẮT BUỘC: Phải lấy lại danh sách lịch hẹn để nạp vào giao diện
        // (Mình giả sử bạn đang lấy ID bệnh nhân từ AuthController giống các hàm trước)
        Long patientId = Long.parseLong(AuthController.getId(request));

        // Giả sử hàm lấy danh sách của bạn tên là getAppointments (bạn sửa lại cho đúng với Service của bạn nhé)
        model.addAttribute("appointments", appointmentService.getMyAppointmentsHistory(patientId));

        // 3. Trả về thẳng tên file HTML
        return "patient/history";
    }
}
