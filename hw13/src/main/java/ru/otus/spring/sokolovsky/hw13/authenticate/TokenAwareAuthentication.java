package ru.otus.spring.sokolovsky.hw13.authenticate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class TokenAwareAuthentication implements Authentication {

    private final Collection<? extends GrantedAuthority> authorities;
    private final String credentials;
    private final String principal;
    private boolean isAuthenticated;
    private String token;

    private static Authentication unauthenticated = new TokenAwareAuthentication();

    public static Authentication fromUserDetails(UserDetails userDetails) {
        return new TokenAwareAuthentication(userDetails);
    }

    static Authentication unauthenticated() {
        return unauthenticated;
    }

    static TokenAwareAuthentication fromToken(String token) {
        return new TokenAwareAuthentication(token);
    }

    private TokenAwareAuthentication(UserDetails userDetails) {
        authorities = userDetails.getAuthorities();
        credentials = userDetails.getPassword();
        principal = userDetails.getUsername();
        isAuthenticated = true;
    }

    private TokenAwareAuthentication(String token) {
        this();
        this.token = token;
    }

    private TokenAwareAuthentication() {
        authorities = Collections.emptyList();
        credentials = null;
        principal = null;
        isAuthenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal;
    }

    String getToken() {
        return token;
    }
}
