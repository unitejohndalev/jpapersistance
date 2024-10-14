package com.perjpasample.jpapersistance.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.main.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUsername(String username);

}
