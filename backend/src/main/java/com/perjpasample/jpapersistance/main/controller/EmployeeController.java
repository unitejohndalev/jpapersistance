package com.perjpasample.jpapersistance.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perjpasample.jpapersistance.main.DTO.EmployeeRequest;
import com.perjpasample.jpapersistance.main.model.EmployeeModel;
import com.perjpasample.jpapersistance.main.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @SuppressWarnings("null")
    @Operation(summary = "Create a new employee", description = "Saves a new employee to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<EmployeeModel> saveEmployee(
            @Parameter(description = "Employee data to be saved") @RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeModel savedEmployee = employeeService.createOrUpdateEmployee(null, employeeRequest);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Retrieve all employees", description = "Returns a list of all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeModel>> getEmployees() {
        List<EmployeeModel> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK); // 200 OK
    }

    @Operation(summary = "Get an employee by ID", description = "Returns a single employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeModel> getEmployeeById(
            @Parameter(description = "ID of the employee to be retrieved") @PathVariable Integer id) {
        EmployeeModel employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @SuppressWarnings("null")
    @Operation(summary = "Update an employee", description = "Updates an existing employee by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeModel> updateEmployeeById(
            @Parameter(description = "ID of the employee to be updated") @PathVariable Integer id,
            @Parameter(description = "Updated employee data") @RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeModel updatedEmployee = employeeService.createOrUpdateEmployee(id, employeeRequest);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK); // 200 OK
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete an employee", description = "Deletes an employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to be deleted") @PathVariable Integer id) {
        EmployeeModel employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @Operation(summary = "Get employees by department ID", description = "Returns a list of employees in a specified department")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeModel>> getEmployeesByDepartmentId(
            @Parameter(description = "Department ID to filter employees") @PathVariable Integer departmentId) {
        List<EmployeeModel> employees = employeeService.getEmployeesByDepartmentId(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Get employees by position ID", description = "Returns a list of employees in a specified position")
    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<EmployeeModel>> getEmployeesByPositionId(
            @Parameter(description = "Position ID to filter employees") @PathVariable Integer positionId) {
        List<EmployeeModel> employees = employeeService.getEmployeesByPositionId(positionId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Count employees by position ID", description = "Returns the count of employees in a specified position")
    @GetMapping("/count/position/{positionId}")
    public ResponseEntity<Long> countEmployeesByPositionId(
            @Parameter(description = "Position ID to count employees") @PathVariable Integer positionId) {
        Long count = employeeService.countEmployeesByPositionId(positionId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
