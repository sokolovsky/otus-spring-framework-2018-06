package ru.otus.spring.sokolovsky.hw12.access;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonBasedLoginFilter extends UsernamePasswordAuthenticationFilter {

    private String body;

    JsonBasedLoginFilter(TokenProviderService tokenProviderService) {
        setAuthenticationSuccessHandler(((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            String username = authentication.getName();
            response.getOutputStream()
                .print("{\"success\": true, \"token\": \""+ tokenProviderService.getToken(username) +"\", \"username\": \""+ username +"\"}");
            response.flushBuffer();
        }));
    }

    // @todo awful fragile
    private String getBody(HttpServletRequest request) {
        String b = null;
        try {
            b = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (b == null || b.trim().equals("")) {
            return body;
        }
        body = b;
        return body;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return getBodyParameter(request, getPasswordParameter());
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return getBodyParameter(request, getUsernameParameter());
    }

    private String getBodyParameter(HttpServletRequest request, String parameter) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String body = getBody(request);
            Map<String, String> map = mapper.readValue(body, new TypeReference<Map<String, String>>(){});
            return map.get(parameter);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
