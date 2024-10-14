package com.perjpasample.jpapersistance.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.main.model.PositionModel;

public interface PositionRepository extends JpaRepository<PositionModel, Integer> {
    
}
