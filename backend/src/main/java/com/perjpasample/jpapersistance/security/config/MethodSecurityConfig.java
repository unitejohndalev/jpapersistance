package com.perjpasample.jpapersistance.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@SuppressWarnings("deprecation")
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{
    
}
