package com.perjpasample.jpapersistance.security.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "session")
public class SessionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Changed to UserModel user


    @ManyToOne
    @JoinColumn(name = "oauth2_user_id")
    private OAuth2AccountModel oauthUser; // Changed to UserModel user


    @Column(name = "session_token")
    private String sessionToken;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "expires_at")
    private Long expiresAt;

    @Column(name = "session_status")
    private Boolean status;



    }

