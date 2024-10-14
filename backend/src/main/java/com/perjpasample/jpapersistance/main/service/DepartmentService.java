package com.perjpasample.jpapersistance.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.main.model.DepartmentModel;
import com.perjpasample.jpapersistance.main.repository.DepartmentRepository;

@Service
public class DepartmentService {

@Autowired
private DepartmentRepository departmentRepository;

public DepartmentModel saveDepartment(DepartmentModel departmentModel) {
return departmentRepository.save(departmentModel);
}

public List<DepartmentModel> getDepartments() {
return departmentRepository.findAll();
}


public DepartmentModel getDepartmentById(Integer id) {
return departmentRepository.findById(id).orElse(null);
}



public DepartmentModel updateDepartmentById(Integer id, DepartmentModel departmentModel) {
departmentModel.setId(id);
return departmentRepository.save(departmentModel);
}


public void deleteDepartment(Integer id) {
departmentRepository.deleteById(id);
}




    
}
