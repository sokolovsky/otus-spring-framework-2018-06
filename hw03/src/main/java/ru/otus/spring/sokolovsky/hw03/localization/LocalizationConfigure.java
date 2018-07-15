package ru.otus.spring.sokolovsky.hw03.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.sokolovsky.hw03.ApplicationProperties;
import ru.otus.spring.sokolovsky.hw03.LocaleCodeSource;

@Configuration
public class LocalizationConfigure {

    @Bean
    @Autowired
    public MessageSource messageSource(ApplicationProperties applicationProperties, LocaleCodeSource localeCodeSource) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String baseName = applicationProperties.getLocaleBundlePath().replace("{code}", localeCodeSource.getCode());
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
