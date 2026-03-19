package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Drill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillRepository extends JpaRepository<Drill, Long> {
}