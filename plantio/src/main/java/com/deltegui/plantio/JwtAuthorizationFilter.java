package com.deltegui.plantio;

import com.deltegui.plantio.users.application.TokenProvider;
import com.deltegui.plantio.users.domain.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String PREFIX = "Bearer ";
    private static final String HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (haveJwtToken(request)) {
            loginWithToken(extractToken(request));
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    private boolean haveJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    private String extractToken(HttpServletRequest request) {
        String tokenHeader = request.getHeader(HEADER);
        return tokenHeader.replace(PREFIX, "");
    }

    private void loginWithToken(String rawToken) {
        var optionalToken = tokenProvider.extractPayload(rawToken);
        if (optionalToken.isEmpty()) {
            SecurityContextHolder.clearContext();
            return;
        }
        Token token = optionalToken.get();
        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            SecurityContextHolder.clearContext();
            return;
        }
        this.setContextToUser(token.getUser());
    }

    private void setContextToUser(String name) {
        var auth = new UsernamePasswordAuthenticationToken(name,null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}

