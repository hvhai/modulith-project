package com.codehunter.modulithproject.gateway.util;

import com.codehunter.modulithproject.gateway.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthenticationUtil {
    private AuthenticationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) auth.getPrincipal();
        String userId = principal.getClaimAsString("sub");
        String username = principal.getClaimAsString("preferred_username");
        return new UserDTO(userId, username);
    }
}
