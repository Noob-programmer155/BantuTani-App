package com.team1.tm.bantutani.app.configuration.security.token;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {
    private TokenManager tokenManager;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<String> ignorePath;
    public TokenFilter(TokenManager tokenManager, List<String> ignorePath) {
        this.tokenManager = tokenManager;
        this.ignorePath = ignorePath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenManager.resolveToken(request);
            if(tokenManager.validateToken(token)) {
                Authentication auth = tokenManager.getAuth(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }}
        catch(NullPointerException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            response.sendError(400, e.getMessage());
        }
        catch(JwtException e) {
            SecurityContextHolder.clearContext();
            response.sendError(403, e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return ignorePath.stream().anyMatch(item -> antPathMatcher.match(item,request.getRequestURI()));
    }
}
