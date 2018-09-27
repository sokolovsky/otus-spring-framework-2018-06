package ru.otus.spring.sokolovsky.hw12.access;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    private TokenProviderService tokenProviderService;

    public TokenAuthenticationManager(TokenProviderService tokenProviderService) {
        this.tokenProviderService = tokenProviderService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (! (authentication instanceof TokenAwareAuthentication)) {
            return null;
        }
        UserDetails userDetails = tokenProviderService.getUserDetails(((TokenAwareAuthentication) authentication).getToken());
        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("Token is not valid");
        }
        return TokenAwareAuthentication.fromUserDetails(userDetails);
    }
}
