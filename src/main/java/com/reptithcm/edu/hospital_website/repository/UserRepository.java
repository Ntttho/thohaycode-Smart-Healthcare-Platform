package com.reptithcm.edu.hospital_website.repository;

import com.reptithcm.edu.hospital_website.model.dto.user.UserDTOResponse;
import com.reptithcm.edu.hospital_website.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    Optional<User> findByEmailWithRole(@Param("email") String email);

    boolean existsByEmail(String email);

    List<User> getUserByRole(String role);

    List<UserRepository> findByDepartmentId(Long departmentId);

    List<User> getUserByRoleAndDepartmentId(String role, Long departmentId);
}
