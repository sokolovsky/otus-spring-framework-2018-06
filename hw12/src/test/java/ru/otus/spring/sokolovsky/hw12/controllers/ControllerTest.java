package ru.otus.spring.sokolovsky.hw12.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.sokolovsky.hw12.access.TokenProviderService;
import ru.otus.spring.sokolovsky.hw12.access.User;
import ru.otus.spring.sokolovsky.hw12.access.UserRepository;
import ru.otus.spring.sokolovsky.hw12.changelogs.SeedCreator;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
class ControllerTest {

    public static final String USER_PASSWORD = "12345";
    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private String token;


    @BeforeEach
    void createFixtures(@Autowired SeedCreator seed, @Autowired TokenProviderService tokenProviderService) {
        seed.create();
        User user = userRepository.findByLogin("user");
        user.setPassword(passwordEncoder.encode(USER_PASSWORD));
        userRepository.save(user);
        token = tokenProviderService.getToken("user");

    }

    MockMvc getRestService(Object... controllers) {
        return standaloneSetup(controllers).addFilter(springSecurityFilterChain).build();
    }

    void injectToken(MockHttpServletRequestBuilder builder) {
        builder.header("X-AuthToken", token);
    }
}
