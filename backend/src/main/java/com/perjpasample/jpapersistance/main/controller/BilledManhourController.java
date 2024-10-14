package com.perjpasample.jpapersistance.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.main.model.BilledManhourModel;
import com.perjpasample.jpapersistance.main.service.BilledManhourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/monthly_report")
public class BilledManhourController {
    @Autowired
    private BilledManhourService billedManhourService;

    @Operation(summary = "Create new billed manhour", description = "Saves a new billed manhour to the database")
    @PostMapping("/billedmanhour")
    public BilledManhourModel saveBilledManhour(
            @Parameter(description = "Billed manhour object to be saved") @RequestBody BilledManhourModel billedManhourModel) {
        return billedManhourService.saveBilledManhour(billedManhourModel);
    }

    
   @Operation(summary = "Retrieve all billed manhours", description = "Returns a list of all billed manhours")
    @GetMapping("/billedmanhour")
    public List<BilledManhourModel> getBilledManhours() {
        return billedManhourService.getBilledManhours();
    }



}
