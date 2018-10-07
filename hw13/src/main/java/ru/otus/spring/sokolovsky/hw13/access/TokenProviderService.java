package ru.otus.spring.sokolovsky.hw13.access;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenProviderService {
    String getToken(String username, String password);

    String getToken(String username);

    UserDetails getUserDetails(String token);
}
