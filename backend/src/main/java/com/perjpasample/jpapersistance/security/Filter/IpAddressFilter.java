package com.perjpasample.jpapersistance.security.Filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Component
@RequiredArgsConstructor
@Order(2)
public class IpAddressFilter extends OncePerRequestFilter {

    private static final List<String> ALLOWED_IPS = Arrays.asList("0:0:0:0:0:0:0:1", "127.0.0.1", "192.168.", "10.");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String remoteAddr = request.getRemoteAddr();

        // Check for allowed IPs
        boolean isAllowed = ALLOWED_IPS.stream().anyMatch(remoteAddr::startsWith) || ALLOWED_IPS.contains(remoteAddr);

        if (!isAllowed) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }
        filterChain.doFilter(request, response);
    }

}
