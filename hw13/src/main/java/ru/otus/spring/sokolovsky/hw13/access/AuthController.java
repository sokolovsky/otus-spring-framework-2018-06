package ru.otus.spring.sokolovsky.hw13.access;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SuppressWarnings("MVCPathVariableInspection")
@RestController
@RequestMapping("${api.base.path:}")
public class AuthController {

    @PostMapping("/token/test")
    public Map authenticationTest() {
        return Collections.EMPTY_MAP;
    }
}
