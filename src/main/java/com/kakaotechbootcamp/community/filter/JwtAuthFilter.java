package com.kakaotechbootcamp.community.filter;

import com.kakaotechbootcamp.community.jwt.JwtTokenExtractor;
import com.kakaotechbootcamp.community.jwt.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtValidator jwtValidator;

    private static final String[] EXCLUDED_PATHS = {
            "/terms", "/privacy"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.startsWith("/auth") && method.equals("POST")) {
            return true;
        }

        if (path.startsWith("/users") && method.equals("POST")) {
            return true;
        }

        return Arrays.stream(EXCLUDED_PATHS).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        Optional<String> token = jwtTokenExtractor.extractAccessToken(request);

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtValidator.validateAndSetAttributes(token.get(), request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            String json = """
                {
                    "success": false,
                    "status": 401,
                    "message": "인증이 필요합니다. 다시 로그인해주세요.",
                    "data": null
                }
            """;

            response.getWriter().write(json);

            return;
        }

        filterChain.doFilter(request, response);
    }
}