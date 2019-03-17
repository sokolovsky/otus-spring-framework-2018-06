package ru.otus.spring.sokolovsky.hw12.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.sokolovsky.hw12.access.AuthController;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"/test-application.properties"})
@WebAppConfiguration
class AccessTest extends ControllerTest {

    @Test
    @DisplayName("Getting token by right credentials")
    void tokenAcquire() throws Exception {
        MockMvc rest = getRestService();

        rest.perform(
                post("/login")
                    .content("{\"login\": \"user\", \"password\": \"12345\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @DisplayName("Getting reject by wrong credentials")
    void tokenReject() throws Exception {
        MockMvc rest = getRestService();

        rest.perform(
                post("/login")
                    .content("{\"login\": \"user\", \"password\": \"wrong\"}")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    @DisplayName("Testing right token")
    void loginTokenTest() throws Exception {
        MockMvc rest = getRestService(new AuthController());

        MockHttpServletRequestBuilder post = post("/token/test");
        injectToken(post);
        rest.perform(post)
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Trying to test wrong token")
    void loginTokenWrongTest() throws Exception {
        MockMvc rest = getRestService(new AuthController());

        MockHttpServletRequestBuilder post = post("/token/test");
        post.header("X-AuthToken", "ewtrerewyteurt");
        rest.perform(post)
            .andExpect(status().is4xxClientError());
    }
}
