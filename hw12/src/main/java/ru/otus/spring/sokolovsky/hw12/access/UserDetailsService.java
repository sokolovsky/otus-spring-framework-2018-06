package ru.otus.spring.sokolovsky.hw12.access;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with login %s not exists", username));
        }

        return loadByUser(user);
    }

    public UserDetails loadByUser(User user) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(GrantedAuthorities.LIBRARY_EDITOR.name()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
