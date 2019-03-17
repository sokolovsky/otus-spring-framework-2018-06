package ru.otus.spring.sokolovsky.hw11.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Service
public class ApiPrefixWebFilter implements WebFilter {

    private String prefix;
    private boolean skip;

    public ApiPrefixWebFilter(@Value("${api.base.path}") String prefix) {
        this.prefix = prefix;
        skip = prefix.equals("");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!skip && path.startsWith(prefix)) {
            return chain.filter(
                    exchange.mutate()
                        .request(exchange.getRequest().mutate().path(path.replaceFirst(prefix, "")).build()).build()
            );
        }
        return chain.filter(exchange);
    }
}
