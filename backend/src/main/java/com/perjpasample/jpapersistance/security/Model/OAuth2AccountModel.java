package com.perjpasample.jpapersistance.security.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "oauth2_accounts")
@NoArgsConstructor
@Getter
@Setter
public class OAuth2AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "used_id")
    private Long id;


    private String username; 
    private String email;

    @Enumerated( EnumType.STRING )
    private Role role ;


    // Other fields, getters, and setters
}
