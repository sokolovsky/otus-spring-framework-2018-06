package ru.otus.spring.sokolovsky.hw13.access;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

@EnableWebSecurity
public class Configuration extends WebSecurityConfigurerAdapter {

    @Value("${api.base.path}")
    private String basePath;

    private UserDetailsService userDetailsService;
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    private TokenProviderService tokenProviderService;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")

    public Configuration(UserDetailsService userDetailsService, TokenAuthenticationProvider tokenAuthenticationProvider, TokenProviderService tokenProviderService) {
        this.userDetailsService = userDetailsService;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.tokenProviderService = tokenProviderService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .addFilterBefore(tokenAuthenticateFilter(), AnonymousAuthenticationFilter.class)
            .addFilterBefore(loginFilter(basePath), AnonymousAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
            .and();
    }

    @Bean
    public Filter tokenAuthenticateFilter() throws Exception {
        return new TokenAuthenticationFilter(authenticationManager());
    }

    private Filter loginFilter(String basePath) throws Exception {
        JsonBasedLoginFilter loginFilter = new JsonBasedLoginFilter(tokenProviderService);
        loginFilter.setAuthenticationManager(authenticationManager());
        loginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(basePath + "/login"));
        loginFilter.setUsernameParameter("login");
        return loginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
            .authenticationProvider(tokenAuthenticationProvider)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
