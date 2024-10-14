package com.perjpasample.jpapersistance.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.perjpasample.jpapersistance.security.Filter.IpAddressFilter;
import com.perjpasample.jpapersistance.security.Filter.JwtAuthenticationFilter;
import com.perjpasample.jpapersistance.security.service.RsaAuthenticationProvider;
import com.perjpasample.jpapersistance.security.service.AesAuthenticationProvider;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final RsaAuthenticationProvider rsaAuthenticationProvider; // Using RSA
    // private final AesAuthenticationProvider aesAuthenticationProvider; // Using
    // AES
    private final IpAddressFilter ipAddressFilter;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000",
                            "http://192.168.40.116:3000/", "http://192.168.40.129:3000", "http://192.168.40.82:3000",
                            "http://192.168.40.143:3000")); // Set allowed origins
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
                    configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration); // Apply CORS config to all endpoints

                    return configuration;
                }))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/",
                            "/home",
                            "/favicon.ico",
                            "/auth/validate",
                            "/monthly_report/billedmanhour",
                            "/api/auth/register",
                            "/session/{id}",
                            "/session",
                            "/session/validate/{token}",
                            "/api/auth/authenticate",
                            "/report/download/pdf",
                            "/report/pdf",
                            "/report/xlsx").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:3000/home", true) // After successful OAuth login
                        .failureUrl("http://localhost:3000/login?error=true")) // In case of failure
                .formLogin(withDefaults()) // Optional, allows form login alongside OAuth2
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .sessionRegistry(sessionRegistry()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ipAddressFilter, JwtAuthenticationFilter.class)
                .authenticationProvider(rsaAuthenticationProvider);

        return http.build();
    }
}