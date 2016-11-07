package org.borowiec.validator;

import org.borowiec.exception.InvalidParameterException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DicewareArgsValidatorTest {

    private static final String EXAMPLE_FILE_NAME = DicewareArgsValidatorTest.class.getClassLoader().getResource("example_file.txt").getPath();

    private DicewareArgsValidator validator = new DicewareArgsValidator();

    @Test
    public void shouldThrowException_whenEmptyArgsArray() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[0]));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.INVALID_NUMBER_OF_PARAMETERS);
    }

    @Test
    public void shouldThrowException_whenMoreThanTwoElementArgsArray() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"1", "2", "3"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.INVALID_NUMBER_OF_PARAMETERS);
    }

    @Test
    public void shouldThrowException_whenFirstArgumentNotAnInteger() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"a", "b"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.MIN_PASSWORD_LENGTH_NOT_A_NUMBER);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentNull() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"1", null}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsEmpty() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"1", ""}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsBlank() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"1", "  "}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessage(DicewareArgsValidator.FILE_NAME_IS_EMPTY);
    }

    @Test
    public void shouldThrowException_whenSecondArgumentIsNotAFileName() throws InvalidParameterException {
        Throwable thrown = catchThrowable(() -> validator.validate(new String[]{"1", "2"}));

        assertThat(thrown).isInstanceOf(InvalidParameterException.class)
                .hasMessageStartingWith(DicewareArgsValidator.NOT_A_FILE);
    }

    @Test
    public void shouldNotThrowException_whenValidArgument() throws InvalidParameterException {
        validator.validate(new String[]{"1"});
    }

    @Test
    public void shouldNotThrowException_whenValidArguments() throws InvalidParameterException {
        validator.validate(new String[]{"1", EXAMPLE_FILE_NAME});
    }

}