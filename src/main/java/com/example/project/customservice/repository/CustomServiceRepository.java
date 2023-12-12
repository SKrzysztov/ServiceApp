package com.example.project.customservice.repository;

import com.example.project.customservice.domain.CustomService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomServiceRepository extends JpaRepository<CustomService,Long> {
}
