package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

    public List<User> findByRole(String role);
}
