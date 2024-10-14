package com.perjpasample.jpapersistance.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.perjpasample.jpapersistance.main.model.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    @Query("SELECT e FROM EmployeeModel e WHERE e.departmentId.id = :departmentId")
    List<EmployeeModel> findEmployeesByDepartmentId(@Param("departmentId") Integer departmentId);

    @Query("SELECT e FROM EmployeeModel e JOIN e.positionId p WHERE p.id = :positionId")
    List<EmployeeModel> findEmployeesByPositionId(@Param("positionId") Integer positionId);

    @Query("SELECT COUNT(e) FROM EmployeeModel e WHERE e.positionId.id = :positionId")
    Long countEmployeesByPositionId(@Param("positionId") Integer positionId);

}
