package com.paperpublish.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private String token;
    private final UserDetails user;

    public TokenBasedAuthentication(UserDetails user) {
        super(user.getAuthorities());
        this.user = user;
    }

    @Override
    public boolean isAuthenticated() {
        return user.isAccountNonLocked();
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
