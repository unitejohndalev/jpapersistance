package com.perjpasample.jpapersistance.security.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.perjpasample.jpapersistance.security.Exception.CustomException.UnauthorizedRequestException;
import com.perjpasample.jpapersistance.security.Repository.jwtUserReporsitory;
import com.perjpasample.jpapersistance.util.KeyPairUtil;

import java.security.*;

@Component
public class RsaAuthenticationProvider implements AuthenticationProvider {
    
    private final jwtUserReporsitory userReporsitory;
    private final KeyPairUtil keyPairUtil;

    public RsaAuthenticationProvider(jwtUserReporsitory userReporsitory, KeyPairUtil keyPairUtil) {
        this.userReporsitory = userReporsitory;
        this.keyPairUtil = keyPairUtil;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();
        var user = userReporsitory.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User ["+ username +"] not found."));
            PrivateKey privateKey = keyPairUtil.getPrivateKey();
            String decryptedPassword = keyPairUtil.decryptingPassword(user.getPassword(), privateKey);
            if (decryptedPassword.equals(inputPassword)) {
                return new UsernamePasswordAuthenticationToken(username, inputPassword, user.getAuthorities());
            } else {
            throw new UnauthorizedRequestException("Invalid entered password.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
