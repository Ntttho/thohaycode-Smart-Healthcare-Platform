package com.reptithcm.edu.hospital_website.service.category;

import com.reptithcm.edu.hospital_website.exception.AppException;
import com.reptithcm.edu.hospital_website.exception.ErrorCode;
import com.reptithcm.edu.hospital_website.model.dto.category.CategoryRequest;
import com.reptithcm.edu.hospital_website.model.entity.Category;
import com.reptithcm.edu.hospital_website.model.entity.User;
import com.reptithcm.edu.hospital_website.repository.CategoryRepository;
import com.reptithcm.edu.hospital_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Category> getCategories(String id){
        // check admin
        checkUser(id);

        return categoryRepository.findAllByIsDelete(false);
    };

    public void addCategory(String id, CategoryRequest request){

        checkUser(id);

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setName(request.getName());

        categoryRepository.save(category);
    }



    @Transactional
    public void updateCategory(String id, CategoryRequest request) {
        checkUser(id);
        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setName(request.getName());
        category.setId(Long.parseLong(request.getId()));
        category.setDelete(false);

        categoryRepository.save(category);
    }

    @Transactional
    public void delete(String userid, Long id) {
        checkUser(userid);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setDelete(true);
        categoryRepository.save(category);
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
