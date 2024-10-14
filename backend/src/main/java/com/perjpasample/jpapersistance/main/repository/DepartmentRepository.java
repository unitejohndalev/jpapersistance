package com.perjpasample.jpapersistance.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.main.model.DepartmentModel;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
    
}
