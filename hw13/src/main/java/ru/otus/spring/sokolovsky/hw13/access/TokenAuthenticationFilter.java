package ru.otus.spring.sokolovsky.hw13.access;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("X-AuthToken");
        if (token == null || token.trim().equals("")) {
            return TokenAwareAuthentication.unauthenticated();
        }
        TokenAwareAuthentication tokenAwareAuthentication = TokenAwareAuthentication.fromToken(token);
        try {
            return authenticationManager.authenticate(tokenAwareAuthentication);
        } catch (AuthenticationException ex) {
            return null;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Authentication auth = attemptAuthentication(httpServletRequest, httpServletResponse);
        if (auth != null && auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
