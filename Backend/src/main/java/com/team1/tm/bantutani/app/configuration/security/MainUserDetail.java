package com.team1.tm.bantutani.app.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class MainUserDetail implements UserDetails, OAuth2User {
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attribute;
    private String password;
    private boolean disable;

    public MainUserDetail(Builder builder) {
        this.name = builder.username;
        this.authorities = builder.authorities;
        this.attribute = builder.attributes;
        this.password = builder.password;
        this.disable = builder.disable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !disable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !disable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !disable;
    }

    @Override
    public boolean isEnabled() {
        return !disable;
    }

    public static class Builder {
        private String username;
        private Collection<? extends GrantedAuthority> authorities;
        private Map<String, Object> attributes;
        private String password;
        private boolean disable;
        public Builder name(String username) {
            this.username = username;
            return this;
        }
        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }
        public Builder attributes(Map<String, Object> attributes) {
            this.attributes = attributes;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder disable(boolean disable) {
            this.disable = disable;
            return this;
        }
        public MainUserDetail build() {
            return new MainUserDetail(this);
        }
    }
}
