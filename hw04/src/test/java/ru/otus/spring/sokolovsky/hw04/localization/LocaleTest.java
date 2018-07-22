package ru.otus.spring.sokolovsky.hw04.localization;

import org.junit.Test;
import org.springframework.context.MessageSource;

import static org.mockito.Mockito.*;

public class LocaleTest {

    @Test
    public void expectedMessageCall() {
        String testedString = "Some string {change}";

        MessageSource mock = mock(MessageSource.class);
        Locale locale = new Locale("en", mock);
        Object[] replaceArgs = {"replace"};
        locale.message(testedString, replaceArgs);
        verify(mock, times(1)).getMessage(eq(testedString), eq(replaceArgs), eq(java.util.Locale.ENGLISH));
    }

    @Test
    public void shortMessageCall() {
        String testedString = "Some string {change}";

        MessageSource mock = mock(MessageSource.class);
        Locale locale = new Locale("ru", mock);
        locale.message(testedString);

        verify(mock, times(1)).getMessage(eq(testedString), eq(new Object[0]), any());
    }

    @Test(expected = RuntimeException.class)
    public void wrongLanguageCode() {
        new Locale("zero", mock(MessageSource.class));
    }
}