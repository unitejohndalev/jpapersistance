package com.perjpasample.jpapersistance.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.main.model.BilledManhourModel;

public interface BilledManhourRepository extends JpaRepository<BilledManhourModel, Long> {

}
