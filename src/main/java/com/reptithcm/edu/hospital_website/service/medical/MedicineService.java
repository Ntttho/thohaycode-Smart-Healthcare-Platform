package com.reptithcm.edu.hospital_website.service.medical;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.category.MedicineRequest;
import com.reptithcm.edu.hospital_website.model.entity.Category;
import com.reptithcm.edu.hospital_website.model.entity.Medicine;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.CategoryRepository;
import com.reptithcm.edu.hospital_website.repository.MedicineRepository;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MedicineRepository medicineRepository;

    public List<Medicine> getMedicinesStatus(String status){
//        checkUser(userId);
        return medicineRepository.getMedicinesByIsApproved(status);
    }

    @Transactional
    public boolean rejectMedicine(String userId, String medicineId){
        checkUser(userId);

        Medicine medicine = medicineRepository.findById(Long.parseLong(medicineId)).orElseThrow(() -> new AppException(ErrorCode.MEDICINE_NOT_FOUND));
        medicine.setIsApproved("reject");
        medicineRepository.save(medicine);
        return true;
    }

    @Transactional
    public void saveMedicine(String userId, MedicineRequest request){
        checkUser(userId);
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setCategory(category);
        medicine.setPrecaution(request.getPrecaution());
        medicine.setDescription(request.getDescription());
        medicine.setAmount(request.getAmount());
        medicine.setIsApproved(request.getIsApproved());
        if(request.getId() != null){
            medicine.setId(request.getId());
        }

        medicineRepository.save(medicine);
    }



















    public void checkUser(String id){
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        if(!user.getRole().equals("admin")){
            throw new AppException(ErrorCode.FORRBIDEN);
        }
    }
}
