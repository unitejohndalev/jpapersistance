package com.perjpasample.jpapersistance.security.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.perjpasample.jpapersistance.security.Exception.CustomException.UnauthorizedRequestException;
import com.perjpasample.jpapersistance.security.Repository.jwtUserReporsitory;
import com.perjpasample.jpapersistance.util.AESCipherUtil;

@Component
public class AesAuthenticationProvider implements AuthenticationProvider {
    
    private final jwtUserReporsitory userReporsitory;
    private final AESCipherUtil aesCipherUtil;

    public AesAuthenticationProvider(jwtUserReporsitory userReporsitory, AESCipherUtil aesCipherUtil) {
        this.userReporsitory = userReporsitory;
        this.aesCipherUtil = aesCipherUtil;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();
        var user = userReporsitory.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User ["+ username +"] not found."));
            String decryptedPassword = aesCipherUtil.decryptingPassword(user.getPassword());
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
