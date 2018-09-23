package ru.otus.spring.sokolovsky.hw12.access;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

import static java.time.LocalDateTime.now;

@Service
public class TokenProviderServiceImpl implements TokenProviderService {

    private static Logger log = Logger.getLogger(TokenProviderServiceImpl.class.getName());

    private UserDetailsService userDetailsService;
    private UserRepository userRepository;
    private String key;
    private int expiresDays;

    public TokenProviderServiceImpl(
            UserDetailsService userDetailsService,
            UserRepository userRepository,
            @Value("${app.token.expiresDays}") String expiresDays,
            @Value("${app.token.key}") String key
    ) {
        this.expiresDays = Integer.parseInt(expiresDays);
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.key = key;
    }

    @Override
    public String getToken(String username, String password) {
        if (Objects.isNull(username) || Objects.isNull(password)) {
            return null;
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (Objects.isNull(user)) {
            return null;
        }
        if (!password.equals(user.getPassword())) {
            return null;
        }
        User storedUser = userRepository.findByLogin(username);

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("clientType", "user");
        tokenData.put("userId", storedUser.getId());
        tokenData.put("username", storedUser.getLogin());
        tokenData.put("token_create_date", now());
        LocalDateTime expirationLocalDate = now().plusDays(expiresDays);
        tokenData.put("token_expiration_date", expirationLocalDate);

        Date expirationDate = Date.from(expirationLocalDate.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setExpiration(expirationDate)
                .setClaims(tokenData)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails getUserDetails(String token) {
        try {
            Map<String, Object> params = (Map<String, Object>) Jwts.parser().setSigningKey(key).parse(token).getBody();
            String userId = (String) params.get("userId");
            if (Objects.isNull(userId)) {
                return null;
            }
            Optional<User> userOptional = userRepository.findById(userId);
            return userOptional.map(user -> userDetailsService.loadByUser(user)).orElse(null);
        } catch (ExpiredJwtException e) {
            log.warning("Attempt to get User with expired token");
            return null;
        }
    }
}
