package org.borowiec.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Map;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DicewarePasswordGeneratorTest {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[1-6]{" + DicewarePasswordGenerator.CODE_DIGITS + "}");

    private DicewarePasswordGenerator passwordGenerator = new DicewarePasswordGenerator();

    @Test
    public void shouldGenerateValidCode() {
        String code = passwordGenerator.generateCode();

        assertThat(CODE_PATTERN.matcher(code).find()).isTrue();
    }

    @Test
    public void shouldGenerateAtLeastFiveLetterPassword() {
        Map<String, String> codeToWord = mock(Map.class);
        when(codeToWord.get(anyString())).thenReturn("geronimo");

        String generatedPassword = passwordGenerator.generatePassword(5, codeToWord);

        assertThat(generatedPassword.length()).isGreaterThanOrEqualTo(5);
    }

    @Test
    public void shouldGenerateExactlyEightLetterPassword() {
        Map<String, String> codeToWord = mock(Map.class);
        when(codeToWord.get(anyString())).thenReturn("12345678");

        String generatedPassword = passwordGenerator.generatePassword(5, codeToWord);

        assertThat(generatedPassword).hasSize(8);
    }

    @Test
    public void shouldGenerateAtLeastThirtyLetterPassword() {
        Map<String, String> codeToWord = mock(Map.class);
        when(codeToWord.get(anyString())).thenAnswer((ignore) -> RandomStringUtils.randomAlphabetic(5, 10));

        String generatedPassword = passwordGenerator.generatePassword(30, codeToWord);

        assertThat(generatedPassword.length()).isGreaterThanOrEqualTo(30);
    }

}