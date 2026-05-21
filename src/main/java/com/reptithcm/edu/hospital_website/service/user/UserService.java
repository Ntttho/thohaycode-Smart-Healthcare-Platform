package com.reptithcm.edu.hospital_website.service.user;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.user.UserDTOResponse;
import com.reptithcm.edu.hospital_website.model.dto.user.UserUpdateProfileRequest;
import com.reptithcm.edu.hospital_website.model.entity.Department;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.DepartmentRepository;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;


    public UserDTOResponse getProfile(String id){
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserDTOResponse.parseFromUser(user);
    }

    @Transactional
    public void updateProfile(String id, UserUpdateProfileRequest request) {
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setDob(request.getDob());

        userRepository.save(user);
    }

    public List<UserDTOResponse> getDoctors(Long departmentId){
        return userRepository.getUserByRoleAndDepartmentId("doctor", departmentId).stream().map(UserDTOResponse::parseFromUser).toList();
    }

    public List<UserRepository> findByDepartmentId(Long departmentId) {
        return userRepository.findByDepartmentId(departmentId);
    }
}
