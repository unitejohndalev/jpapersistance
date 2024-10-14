package com.perjpasample.jpapersistance.security.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perjpasample.jpapersistance.security.Model.SessionModel;
import com.perjpasample.jpapersistance.security.Repository.SessionRepository;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository sessionRepository;

    public SessionModel save(SessionModel sessionModel) {
        return sessionRepository.save(sessionModel);
    }

      // Validate session by token
    public boolean isSessionValid(String token) {
        return sessionRepository.findBySessionToken(token)
                .map(session -> session.getExpiresAt() > Instant.now().getEpochSecond())
                .orElse(false); // Session invalid if not found
    }

    // Invalidate session by deleting it (optional for logout)
    public void invalidateSession(String token) {
        sessionRepository.findBySessionToken(token).ifPresent(sessionRepository::delete);
    }

    public List<SessionModel> getSessions() {
        return sessionRepository.findAll();
    }

    public SessionModel getSessionById(Integer id) {
        return sessionRepository.findById(id).orElse(null);
    }

   

    // Delete session by id
    public void deleteSessionById(Integer id) {
        sessionRepository.deleteById(id);
    }


    
    public void delete(SessionModel session) {
        sessionRepository.delete(session);
    }

    public List<SessionModel> findByUserId(Integer userId) {
        return sessionRepository.findByUserId(userId);
    }

        // Add this method to SessionService
    public void logout(String sessionToken) {
        invalidateSession(sessionToken); // Call the existing method to delete the session by token
    }

    public List<SessionModel> findByUserId(Long userId) {
        return sessionRepository.findByOauthUserId(userId); // Assuming you have a repository method
    }

   
}
