package com.perjpasample.jpapersistance.security.service;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    public String getUsername(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
        // Get the name of the user from the OAuth2 provider
        return authentication.getPrincipal().getAttribute("name");
    }

    public String getEmail(OAuth2AuthenticationToken authentication) {
        // Assuming the email is in the "email" attribute
        return authentication.getPrincipal().getAttribute("email");
    }
    
}
