package com.perjpasample.jpapersistance.security.Controller;

import com.perjpasample.jpapersistance.security.Model.OAuth2AccountModel;
import com.perjpasample.jpapersistance.security.Model.SessionModel;
import com.perjpasample.jpapersistance.security.Model.Role;
import com.perjpasample.jpapersistance.security.service.OAuth2AccountService;
import com.perjpasample.jpapersistance.security.service.OAuth2Service;
import com.perjpasample.jpapersistance.security.service.SessionService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@RestController
public class OAuth2Controller {
    
 private final OAuth2Service oAuth2Service;
    private final OAuth2AccountService oAuth2AccountService;
    private final SessionService sessionService; // Service for managing sessions
    
    public OAuth2Controller(OAuth2Service oAuth2Service, OAuth2AccountService oAuth2AccountService,
            SessionService sessionService) {
    this.oAuth2Service = oAuth2Service;
        this.oAuth2AccountService = oAuth2AccountService;
        this.sessionService = sessionService; // Inject SessionService
}

@GetMapping("/home")
    public Map<String, Object> home(OAuth2AuthenticationToken authentication) {
    String username = oAuth2Service.getUsername(authentication);
        String email = oAuth2Service.getEmail(authentication);

        // Create an OAuth2AccountModel instance with the retrieved data
        OAuth2AccountModel account = new OAuth2AccountModel();
        account.setUsername(username);
        account.setEmail(email);
        account.setRole(Role.ROLE_USER); // Set the default role

        // Save or retrieve the existing OAuth2 account
        OAuth2AccountModel savedAccount = oAuth2AccountService.saveOAuth2Account(account);

        List<SessionModel> existingSessions = sessionService.findByUserId(savedAccount.getId());
        existingSessions.forEach(session -> {
            session.setStatus(false); // Set status to false
            sessionService.save(session); // Save changes to the session
        });

        // Create a new session for the OAuth2 user
        SessionModel session = new SessionModel();
        session.setOauthUser(savedAccount); // Associate the saved OAuth2 user
        session.setSessionToken(generateSessionToken()); // Generate a secure session token
        session.setCreatedAt(System.currentTimeMillis());
        session.setExpiresAt(System.currentTimeMillis() + (60 * 60 * 1000)); // Session expires in 1 hour
        session.setStatus(true); // Active session

        sessionService.save(session); // Save session to the database

        // Prepare the response
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        userDetails.put("email", email);
        userDetails.put("sessionId", session.getId()); // Return session ID if needed
        userDetails.put("sessionToken", session.getSessionToken()); // Return session token
        userDetails.put("userRole", savedAccount.getRole());
        userDetails.put("userId", savedAccount.getId());
        return userDetails; // Return a map containing username, email, sessionId, and sessionToken
    }

    private String generateSessionToken() {
        // Generate a secure random session token (you can customize this as needed)
        return UUID.randomUUID().toString();
    }
}