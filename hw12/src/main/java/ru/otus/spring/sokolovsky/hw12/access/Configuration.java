package ru.otus.spring.sokolovsky.hw12.access;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

@EnableWebSecurity
public class Configuration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private TokenAuthenticationManager tokenAuthenticationManager;

    public Configuration(UserDetailsService userDetailsService, TokenAuthenticationManager tokenAuthenticationManager) {
        this.userDetailsService = userDetailsService;
        this.tokenAuthenticationManager = tokenAuthenticationManager;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
//            .headers().frameOptions().sameOrigin()
//            .and()
            .addFilterBefore(tokenAuthenticateFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated();
//            .antMatchers(HttpMethod.POST).permitAll();
//            .authenticated();
//            .and()
//            .formLogin()
//            .loginProcessingUrl("/login")
//            .passwordParameter("password")
//            .usernameParameter("login");
    }

    @Bean
    public Filter tokenAuthenticateFilter() {
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(new AntPathRequestMatcher("/**", "POST"));
        filter.setAuthenticationManager(tokenAuthenticationManager);
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
