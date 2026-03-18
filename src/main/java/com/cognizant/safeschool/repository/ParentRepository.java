package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    public Parent findParentByEmail(String email);
}
