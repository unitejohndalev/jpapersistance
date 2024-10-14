package com.perjpasample.jpapersistance.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.main.DTO.EmployeeRequest;
import com.perjpasample.jpapersistance.main.model.DepartmentModel;
import com.perjpasample.jpapersistance.main.model.EmployeeModel;
import com.perjpasample.jpapersistance.main.model.PositionModel;
import com.perjpasample.jpapersistance.main.model.UserModel;
import com.perjpasample.jpapersistance.main.repository.DepartmentRepository;
import com.perjpasample.jpapersistance.main.repository.EmployeeRepository;
import com.perjpasample.jpapersistance.main.repository.PositionRepository;
import com.perjpasample.jpapersistance.main.repository.UserRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeModel createOrUpdateEmployee(Integer id, EmployeeRequest employeeRequest) {
        UserModel user = userRepository.findById(employeeRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        PositionModel position = positionRepository.findById(employeeRequest.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));
        DepartmentModel department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        EmployeeModel employeeModel = (id != null)
                ? employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"))
                : new EmployeeModel();
        employeeModel.setUserId(user);
        employeeModel.setPositionId(position);
        employeeModel.setDepartmentId(department);

        return employeeRepository.save(employeeModel);
    }

    public List<EmployeeModel> getEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeModel getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeModel updateEmployeeById(Integer id, EmployeeModel employeeModel) {
        Optional<EmployeeModel> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            EmployeeModel employeeToUpdate = existingEmployee.get();
            employeeToUpdate.setUserId(employeeModel.getUserId());
            employeeToUpdate.setPositionId(employeeModel.getPositionId());
            employeeToUpdate.setDepartmentId(employeeModel.getDepartmentId());
            return employeeRepository.save(employeeToUpdate);
        }
        return null;
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeModel> getEmployeesByDepartmentId(Integer departmentId) {
        return employeeRepository.findEmployeesByDepartmentId(departmentId);
    }

    public List<EmployeeModel> getEmployeesByPositionId(Integer positionId) {
        return employeeRepository.findEmployeesByPositionId(positionId);
    }

    public Long countEmployeesByPositionId(Integer positionId) {
        return employeeRepository.countEmployeesByPositionId(positionId);
    }
}
