package com.perjpasample.jpapersistance.security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perjpasample.jpapersistance.security.Model.OAuth2AccountModel;
import com.perjpasample.jpapersistance.security.Model.Provider;


@Repository
public interface OAuth2AccountRepository extends JpaRepository<OAuth2AccountModel, Long> {

    Optional<OAuth2AccountModel> findByUsername(String username);

}
