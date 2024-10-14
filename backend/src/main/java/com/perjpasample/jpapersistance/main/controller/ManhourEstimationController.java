package com.perjpasample.jpapersistance.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.main.model.ManhourEstimationModel;
import com.perjpasample.jpapersistance.main.service.ManhourEstimationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/employee")
public class ManhourEstimationController {

    @Autowired
    private ManhourEstimationService manhourEstimationService;

    @SuppressWarnings("null")
    @Operation(summary = "Create a new manhour estimation", description = "Saves a new manhour estimation to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Manhour estimation created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/manhour-estimation")
    public ResponseEntity<ManhourEstimationModel> saveManhourEstimation(
            @Parameter(description = "Manhour estimation data to be saved") @RequestBody ManhourEstimationModel manhourEstimationModel) {
        try {
            ManhourEstimationModel savedManhourEstimation = manhourEstimationService.save(manhourEstimationModel);
            return new ResponseEntity<>(savedManhourEstimation, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
