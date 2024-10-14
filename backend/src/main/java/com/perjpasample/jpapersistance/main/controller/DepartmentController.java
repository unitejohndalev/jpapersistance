package com.perjpasample.jpapersistance.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.perjpasample.jpapersistance.main.model.DepartmentModel;
import com.perjpasample.jpapersistance.main.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/department") // Define a base path for the controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = " aCreate new department", description = "Saves a new department to the database")
    @PostMapping
    public DepartmentModel saveDepartment(
            @Parameter(description = "Department object to be saved") @RequestBody DepartmentModel departmentModel) {
        return departmentService.saveDepartment(departmentModel);
    }

    @Operation(summary = "Retrieve all departments", description = "Returns a list of all departments")
    @GetMapping
    public List<DepartmentModel> getDepartments() {
        return departmentService.getDepartments();
    }

    @Operation(summary = "Get a department by ID", description = "Returns a department by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department found"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/{id}")
    public DepartmentModel getDepartmentById(
            @Parameter(description = "ID of the department to be retrieved") @PathVariable Integer id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(summary = "Update a department", description = "Updates an existing department by its ID")
    @PutMapping("/{id}")
    public DepartmentModel updateDepartmentById(
            @Parameter(description = "ID of the department to be updated") @PathVariable Integer id,
            @Parameter(description = "Updated department object") @RequestBody DepartmentModel departmentModel) {
        return departmentService.updateDepartmentById(id, departmentModel);
    }

    @Operation(summary = "Delete a department", description = "Deletes a department by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Department deleted"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public void deleteDepartment(
            @Parameter(description = "ID of the department to be deleted") @PathVariable Integer id) {
        departmentService.deleteDepartment(id);
    }
}
