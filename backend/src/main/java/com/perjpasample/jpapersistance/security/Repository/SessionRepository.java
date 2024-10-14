package com.perjpasample.jpapersistance.security.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.perjpasample.jpapersistance.security.Model.SessionModel;

public interface SessionRepository extends JpaRepository<SessionModel, Integer> {

    Optional<SessionModel> findBySessionToken(String token);
    
    List<SessionModel> findByUserId(Integer userId);

    List<SessionModel> findByOauthUserId(Long userId);

    
}
