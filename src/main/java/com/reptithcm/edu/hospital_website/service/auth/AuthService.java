package com.reptithcm.edu.hospital_website.service.auth;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.user.UserDTOResponse;
import com.reptithcm.edu.hospital_website.model.dto.user.UserRegisterRequest;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import com.reptithcm.edu.hospital_website.ultility.Hashing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserDTOResponse userLogin(String email, String password){

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!Hashing.encoding(password).equals(user.getPassword())){
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        return UserDTOResponse.builder()
                .id(user.getId()).email(user.getEmail()).fullName(user.getFullName())
                .phone(user.getPhone()).gender(user.getGender()).dob(user.getDob())
                .role(user.getRole()).
                build();
    }

    @Transactional
    public void userRegister(UserRegisterRequest request){
        boolean check = userRepository.existsByEmail(request.getEmail());
        if(check){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(Hashing.encoding(request.getPassword()));
        user.setGender(request.getGender());
        user.setFullName(request.getFullName());
        user.setDob(request.getDob());
        user.setRole("patient");
        user.setPhone(request.getPhone());

        userRepository.save(user);
    }


}
