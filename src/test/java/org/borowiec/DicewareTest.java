package org.borowiec;

import org.borowiec.exception.InvalidParameterException;
import org.borowiec.exception.InvalidWordCountException;
import org.borowiec.generator.DicewarePasswordGenerator;
import org.borowiec.reader.DicewareWordListFileReader;
import org.borowiec.validator.DicewareArgsValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DicewareTest {

    @Mock
    private DicewareArgsValidator argsValidator;
    @Mock
    private DicewareWordListFileReader wordListFileReader;
    @Mock
    private DicewarePasswordGenerator passwordGenerator;

    @Mock
    private PrintStream printStream;

    private StringBuilder outputBuffer;

    private Diceware diceware;

    @Before
    public void before() {
        outputBuffer = new StringBuilder();

        doAnswer((invocationOnMock) -> outputBuffer.append(invocationOnMock.getArgumentAt(0, String.class)).append("\n"))
                .when(printStream).println(anyString());

        System.setOut(printStream);

        diceware = new Diceware(argsValidator, wordListFileReader, passwordGenerator);
    }

    @Test
    public void shouldPrintErrorAndUsage_whenValidationException() throws InvalidParameterException {
        doThrow(new InvalidParameterException("message")).when(argsValidator).validate(any());

        diceware.run(new String[0]);

        assertErrorAndUsagePrinted();
    }

    @Test
    public void shouldPrintErrorAndUsage_whenIOException() throws Exception {
        when(wordListFileReader.read(any(File.class))).thenThrow(new IOException("message"));

        diceware.run(new String[]{"1", "filename"});

        assertErrorAndUsagePrinted();
    }

    @Test
    public void shouldPrintErrorAndUsage_whenInvalidWordCountException() throws Exception {
        when(wordListFileReader.read(any(File.class))).thenThrow(new InvalidWordCountException("message"));

        diceware.run(new String[]{"1", "filename"});

        assertErrorAndUsagePrinted();
    }

    @Test
    public void shouldPrintResult_forHappyPath() throws Exception {
        when(passwordGenerator.generatePassword(anyInt(), anyMapOf(String.class, String.class))).thenReturn("test");

        diceware.run(new String[]{"1", "filename"});

        assertThat(outputBuffer.toString()).isEqualTo("Generated password: test\nPassword length: 4\n");
    }

    private void assertErrorAndUsagePrinted() {
        assertThat(outputBuffer).startsWith("Error: message");
        assertThat(outputBuffer).contains("Usage:");
    }

}