package ru.otus.spring.sokolovsky.hw12.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    private final String baseApiPath;

    public WebConfiguration(@Value("${api.base.path}") String baseApiPath) {
        this.baseApiPath = baseApiPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.png", "/**/*.ttf", "/**/*.woff", "/**/*.woff2")
            .setCachePeriod(0)
            .addResourceLocations("classpath:/public/");

        registry
            .addResourceHandler("/", "/**")
            .setCachePeriod(0)
            .addResourceLocations("classpath:/public/index.html")
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    if (resourcePath.startsWith(baseApiPath) || resourcePath.startsWith(baseApiPath.substring(1))) {
                        return null;
                    }

                    return location.exists() && location.isReadable() ? location : null;
                }
            });
    }
}
