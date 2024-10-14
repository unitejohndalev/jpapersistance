package com.perjpasample.jpapersistance.main.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.main.model.UserModel;
import com.perjpasample.jpapersistance.main.service.UserService;

import net.sf.jasperreports.engine.JRException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user", description = "Saves a new user to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/user")
    public UserModel saveUser(@Parameter(description = "User data to be saved") @RequestBody UserModel userModel) {
        return userService.saveUser(userModel);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<UserModel> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{id}")
    public UserModel getUserById(@Parameter(description = "ID of the user to retrieve") @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Update user by ID", description = "Updates an existing user based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/user/{id}")
    public UserModel updateUserById(
            @Parameter(description = "ID of the user to update") @PathVariable Integer id,
            @Parameter(description = "Updated user data") @RequestBody UserModel userModel) {
        return userService.updateUserById(id, userModel);
    }

    @Operation(summary = "Delete user by ID", description = "Deletes a user based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/user/{id}")
    public void deleteUser(@Parameter(description = "ID of the user to delete") @PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Generate report", description = "Generates a report in the specified format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid format")
    })
    @GetMapping("/report/{format}")
    public String generateReport(
            @Parameter(description = "Format of the report to generate") @PathVariable String format)
            throws JRException, IOException {
        return userService.exportReport(format);
    }

    @GetMapping("/report/download/pdf")
    public ResponseEntity<byte[]> downloadPdfReport() throws FileNotFoundException, JRException {
        byte[] report = userService.exportPdfReport();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(report);
    }

    @GetMapping("/report/conversion/excel")
    public ResponseEntity<String> convertPdfToExcel() throws IOException {
        String pdfPath = "C:\\SpringTools\\Reports\\Simple_Blue.pdf";
        String excelPath = "C:\\SpringTools\\Reports\\output.xlsx";

        String result = userService.convertPdfToExcel(pdfPath, excelPath);

        return ResponseEntity.ok(result);
    }

     @GetMapping("/api/user")
    public OAuth2User getUser(@AuthenticationPrincipal OAuth2User principal) {
        return principal; // This returns the user's details
    }

    @GetMapping("/")
    public String home() {
        return "Hello, Home!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, Secured!";
    }
}
