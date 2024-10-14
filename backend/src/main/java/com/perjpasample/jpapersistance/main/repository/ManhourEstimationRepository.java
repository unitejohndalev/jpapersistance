package com.perjpasample.jpapersistance.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.main.model.ManhourEstimationModel;

public interface ManhourEstimationRepository extends JpaRepository<ManhourEstimationModel, Integer> {
    
}
