package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    void removeCategoriesById(Long id);

    List<Category> findAllByIsDelete(boolean isDelete);
}
