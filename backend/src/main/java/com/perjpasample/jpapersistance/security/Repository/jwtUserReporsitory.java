package com.perjpasample.jpapersistance.security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perjpasample.jpapersistance.security.Model.User;

public interface jwtUserReporsitory extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
