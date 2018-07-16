package ru.otus.spring.sokolovsky.hw03;

import org.junit.Test;
import org.springframework.boot.ApplicationArguments;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class LocaleCodeSourceTest {

    @Test
    public void shouldUseDefaultCode() {
        ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
        when(applicationArguments.getSourceArgs()).thenReturn(new String[0]);

        LocaleCodeSource localeCodeSource = new LocaleCodeSource(applicationArguments);
        assertThat(localeCodeSource.getCode(), is(java.util.Locale.getDefault().getLanguage()));
    }

    @Test(expected = RuntimeException.class)
    public void wrongLanguageCode() {
        ApplicationArguments applicationArguments = mock(ApplicationArguments.class);
        when(applicationArguments.getSourceArgs()).thenReturn(new String[]{"rus"});
        new LocaleCodeSource(applicationArguments);
    }
}
