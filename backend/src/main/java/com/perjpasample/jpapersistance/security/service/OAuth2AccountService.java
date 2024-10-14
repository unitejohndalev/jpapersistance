package com.perjpasample.jpapersistance.security.service;

import org.springframework.stereotype.Service;
import java.util.Optional;

import com.perjpasample.jpapersistance.security.Model.OAuth2AccountModel;
import com.perjpasample.jpapersistance.security.Repository.OAuth2AccountRepository;

@Service
public class OAuth2AccountService {
    private final OAuth2AccountRepository oAuth2AccountRepository;

    public OAuth2AccountService(OAuth2AccountRepository oAuth2AccountRepository) {
        this.oAuth2AccountRepository = oAuth2AccountRepository;
    }

    public OAuth2AccountModel saveOAuth2Account(OAuth2AccountModel account) {
        // Check if a user with the same username already exists
        Optional<OAuth2AccountModel> existingAccountOpt = oAuth2AccountRepository.findByUsername(account.getUsername());
        if (existingAccountOpt.isPresent()) {
            // User already exists, return the existing account
            return existingAccountOpt.get();
        } else {
            // Save new account
            return oAuth2AccountRepository.save(account);
        }
    }
}
