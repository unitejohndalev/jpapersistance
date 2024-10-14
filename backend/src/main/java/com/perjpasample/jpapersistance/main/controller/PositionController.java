package com.perjpasample.jpapersistance.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.main.model.PositionModel;
import com.perjpasample.jpapersistance.main.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Operation(summary = "Create a new position", description = "Saves a new position to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Position created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/position")
    public PositionModel savePosition(
            @Parameter(description = "Position data to be saved") @RequestBody PositionModel positionModel) {
        return positionService.savePosition(positionModel);
    }

    @Operation(summary = "Get all positions", description = "Retrieves a list of all positions")
    @ApiResponse(responseCode = "200", description = "List of positions retrieved successfully")
    @GetMapping("/position")
    public List<PositionModel> getPositions() {
        return positionService.getPositions();
    }

    @Operation(summary = "Update position by ID", description = "Updates an existing position based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Position updated"),
            @ApiResponse(responseCode = "404", description = "Position not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/position/{id}")
    public PositionModel updatePositionById(
            @Parameter(description = "ID of the position to update") @PathVariable Integer id,
            @Parameter(description = "Updated position data") @RequestBody PositionModel positionModel) {
        return positionService.updatePositionById(id, positionModel);
    }

    @Operation(summary = "Delete position by ID", description = "Deletes a position based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Position deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Position not found")
    })
    @DeleteMapping("/position/{id}")
    public void deletePosition(@Parameter(description = "ID of the position to delete") @PathVariable Integer id) {
        positionService.deletePosition(id);
    }
}
