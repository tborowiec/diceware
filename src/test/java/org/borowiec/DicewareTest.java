package org.borowiec;

import org.borowiec.exception.InvalidParameterException;
import org.borowiec.exception.InvalidWordCountException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DicewareTest {

    private static final String VALID_FILE_NAME = DicewareTest.class.getClassLoader().getResource("eff_large_wordlist.txt").getPath();
    private static final String INVALID_FILE_NAME = DicewareTest.class.getClassLoader().getResource("example_word_list.txt").getPath();

    private static final Pattern CODE_PATTERN = Pattern.compile("^[1-6]{" + Diceware.CODE_DIGITS + "}");

    private Diceware diceware = new Diceware();

    @Test
    public void shouldThrowException_whenEmptyArgsArray() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[0]));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.INVALID_NUMBER_OF_PARAMETERS);
    }

    @Test
    public void shouldThrowException_whenOneElementArgsArray() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.INVALID_NUMBER_OF_PARAMETERS);
    }

    @Test
    public void shouldThrowException_whenMoreThanTwoElementArgsArray() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1", "2", "3"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.INVALID_NUMBER_OF_PARAMETERS);
    }

    @Test
    public void shouldThrowException_whenFirstArgumentNotAnInteger() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"a", "b"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.MIN_PASSWORD_LENGTH_NOT_A_NUMBER);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentNull() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1", null}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsEmpty() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1", ""}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsBlank() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1", "  "}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(Diceware.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsNotAFileName() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> diceware.validateAndAssignInputParameters(new String[]{"1", "2"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith(Diceware.NOT_A_FILE);
    }

    @Test
    public void shouldSetParameters_forHappyPath() throws InvalidParameterException {
        diceware.validateAndAssignInputParameters(new String[]{"1", VALID_FILE_NAME});

        assertThat(diceware.minPasswordLength).isEqualTo(1);
        assertThat(diceware.wordListFile.getAbsolutePath()).isEqualTo(VALID_FILE_NAME);
    }

    @Test
    public void shouldThrowException_forInvalidFile() throws IOException, InvalidWordCountException {
        diceware.wordListFile = new File(INVALID_FILE_NAME);

        Throwable thrown = catchThrowable(() -> diceware.readWordList());

        assertThat(thrown).isInstanceOf(InvalidWordCountException.class)
                .hasMessageStartingWith(Diceware.INVALID_WORD_COUNT);
    }

    @Test
    public void shouldReadWordList_forExistingFile() throws IOException, InvalidWordCountException {
        diceware.wordListFile = new File(VALID_FILE_NAME);

        diceware.readWordList();

        assertThat(diceware.codeToWord).containsKeys("11111", "11112", "11113", "11114", "11115", "11116");
        assertThat(diceware.codeToWord).containsValues("abacus", "abdomen", "abdominal", "abide", "abiding", "ability");
    }

    @Test
    public void shouldGenerateValidCode() {
        String code = diceware.generateCode();

        assertThat(CODE_PATTERN.matcher(code).find()).isTrue();
    }

}