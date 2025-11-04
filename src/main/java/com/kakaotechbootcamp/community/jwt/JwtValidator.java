package com.kakaotechbootcamp.community.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final JwtProvider jwtProvider;

    public boolean validateAndSetAttributes(String token, HttpServletRequest request) {
        try {
            var jws = jwtProvider.parse(token);
            Claims body = jws.getBody();
            request.setAttribute("userId", Long.valueOf(body.getSubject()));

            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}