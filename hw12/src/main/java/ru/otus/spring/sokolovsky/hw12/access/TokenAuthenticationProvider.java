package ru.otus.spring.sokolovsky.hw12.access;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenProviderService tokenProviderService;

    public TokenAuthenticationProvider(TokenProviderService tokenProviderService) {
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

    @Override
    public boolean supports(Class<?> authentication) {
        Class<?> aClass = authentication;
        while (aClass != Object.class) {
            if (aClass != TokenAwareAuthentication.class) {
                aClass = aClass.getSuperclass();
                continue;
            }
            return true;
        }
        return false;
    }
}
