package com.perjpasample.jpapersistance.security.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perjpasample.jpapersistance.security.Model.AuthenticationRequest;
import com.perjpasample.jpapersistance.security.Model.AuthenticationResponse;
import com.perjpasample.jpapersistance.security.Model.RegisterRequest;
import com.perjpasample.jpapersistance.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        //return ResponseEntity.ok(service.register(request));
        return ResponseEntity.ok(service.rsaRegister(request));
        //return ResponseEntity.ok(service.aesRegister(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {
        //return ResponseEntity.ok(service.authenticate(request));
        return ResponseEntity.ok(service.rsaAuthenticate(request));
        //return ResponseEntity.ok(service.aesAuthenticate(request));
    }

}
