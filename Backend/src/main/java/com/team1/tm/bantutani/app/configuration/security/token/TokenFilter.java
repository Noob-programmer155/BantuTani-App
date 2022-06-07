package com.team1.tm.bantutani.app.configuration.security.token;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends OncePerRequestFilter {
    private TokenManager tokenManager;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<String> ignorePath;
    public TokenFilter(Builder builder) {
        this.tokenManager = builder.tokenManager;
        this.ignorePath = builder.ignorePath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String token = tokenManager.resolveToken(request);
            if(tokenManager.validateToken(token)) {
                Authentication auth = tokenManager.getAuth(token,request);
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(request, response);
                return;
            }
            SecurityContextHolder.clearContext();
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"wrong token value\"}");
        }
        catch(NullPointerException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\""+e.getMessage()+"\"}");
        }
        catch(JwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\""+e.getMessage()+"\"}");
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(500);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\""+e.getMessage()+"\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return ignorePath.stream().anyMatch(item -> antPathMatcher.match(item,request.getRequestURI()));
    }

    public static class Builder {
        private List<String> ignorePath = new ArrayList<>();
        private TokenManager tokenManager;
        public Builder addPath(List<String> path) {
            this.ignorePath.addAll(path);
            return this;
        }
        public Builder addTokenManager(TokenManager tokenManager) {
            this.tokenManager = tokenManager;
            return this;
        }
        public TokenFilter build() {
            return new TokenFilter(this);
        }
    }
}
