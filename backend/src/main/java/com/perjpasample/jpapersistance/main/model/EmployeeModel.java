package com.perjpasample.jpapersistance.main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class EmployeeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emp_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @ManyToOne
    @JoinColumn(name = "position_id") 
    private PositionModel positionId;

    @ManyToOne
    @JoinColumn(name = "department_id") 
    private DepartmentModel departmentId;


    public EmployeeModel() {
    }

    public EmployeeModel(Integer emp_id, UserModel userId,
     PositionModel positionId, DepartmentModel departmentId) {
        this.emp_id = emp_id;
        this.userId = userId;
        this.positionId = positionId;
        this.departmentId = departmentId;
    }


    public Integer getEmp_id() {
        return this.emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public UserModel getUserId() {
        return this.userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
    }

    public PositionModel getPositionId() {
        return this.positionId;
    }

    public void setPositionId(PositionModel positionId) {
        this.positionId = positionId;
    }

    public DepartmentModel getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(DepartmentModel departmentId) {
        this.departmentId = departmentId;
    }
}
