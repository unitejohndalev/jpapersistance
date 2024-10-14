package com.perjpasample.jpapersistance.security.service;

import java.security.KeyPair;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.security.Exception.CustomException.UnauthorizedRequestException;
import com.perjpasample.jpapersistance.security.Model.AuthenticationRequest;
import com.perjpasample.jpapersistance.security.Model.AuthenticationResponse;
import com.perjpasample.jpapersistance.security.Model.RegisterRequest;
import com.perjpasample.jpapersistance.security.Model.Role;
import com.perjpasample.jpapersistance.security.Model.SessionModel;
import com.perjpasample.jpapersistance.security.Model.User;
import com.perjpasample.jpapersistance.security.Repository.jwtUserReporsitory;
import com.perjpasample.jpapersistance.util.KeyPairUtil;
import com.perjpasample.jpapersistance.util.AESCipherUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final jwtUserReporsitory reporsitory;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RsaAuthenticationProvider rsaAuthenticationProvider;
    private final AesAuthenticationProvider aesAuthenticationProvider;
    private final KeyPairUtil keyPairUtil;
    private final AESCipherUtil aesCipherUtil;
        private final SessionService sessionService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        reporsitory.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Using RSA Key for encrypting password
    public AuthenticationResponse rsaRegister(RegisterRequest request) {

                // Load Public Key for Encryption
                PublicKey publicKey = keyPairUtil.getPublicKey();

                // Encrypt User Password
                String encryptedUserPassword = keyPairUtil.encryptingPassword(request.getPassword(), publicKey);
                
                var user = User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(encryptedUserPassword)
                        .role(Role.ROLE_USER)
                        .build();
                reporsitory.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
    }

    // Using AES Key for encrypting password
    public AuthenticationResponse aesRegister(RegisterRequest request) {

        // Encrypt User Password
        String encryptedUserPassword = aesCipherUtil.encryptingPassword(request.getPassword());
        
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encryptedUserPassword)
                .role(Role.ROLE_USER)
                .build();
        reporsitory.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
}

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                                request.getPassword()));
        var user = reporsitory.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    // Using RSA Key for decrypting password
    public AuthenticationResponse rsaAuthenticate(AuthenticationRequest request) {
        Authentication auth = rsaAuthenticationProvider.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Authentication is successful
        var user = reporsitory.findByUsername(auth.getName())
                                .orElseThrow(() -> new UnauthorizedRequestException(
                                                "User [" + auth.getName() + "] not found"));

                List<SessionModel> existingSessions = sessionService.findByUserId(user.getId());
 
                // Mark all existing sessions as inactive
                existingSessions.forEach(session -> {
                        session.setStatus(false); // Set session as inactive
                        sessionService.save(session); // Save the updated session
                });

                // Create a new session
                String sessionToken = UUID.randomUUID().toString();
                SessionModel session = new SessionModel();
                session.setUser(user);
                session.setSessionToken(sessionToken);
                session.setCreatedAt(Instant.now().getEpochSecond());
                session.setExpiresAt(Instant.now().getEpochSecond() + 3600); // 1 hour later
                session.setStatus(true); // Set session as active
                sessionService.save(session);

        var jwtToken = jwtService.generateToken(user);

                // Build the response, including session information
                Map<String, Object> response = new HashMap<>();
                response.put("token", jwtToken); // Add JWT token to response
                response.put("sessionToken", sessionToken); // Include session token
                response.put("sessionId", session.getId()); // Include session ID
                response.put("userId", user.getId()); // Add user ID for reference

                return AuthenticationResponse.builder().token(jwtToken).additionalDetails(response).build();
    }

    // Using AES Key for decrypting password
    public AuthenticationResponse aesAuthenticate(AuthenticationRequest request) {
        Authentication auth = aesAuthenticationProvider.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Authentication is successful
        var user = reporsitory.findByUsername(auth.getName())
                                .orElseThrow(() -> new UnauthorizedRequestException(
                                                "User [" + auth.getName() + "] not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
