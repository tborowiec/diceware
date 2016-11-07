package org.borowiec.reader;

import org.borowiec.exception.InvalidWordCountException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DicewareWordListReaderTest {

    private static final ClassLoader classLoader = DicewareWordListReaderTest.class.getClassLoader();

    private static final InputStream VALID_FILE_INPUT_STREAM = classLoader.getResourceAsStream("eff_large_wordlist.txt");
    private static final InputStream ENGLISH_WORD_LIST_INPUT_STREAM = classLoader.getResourceAsStream("diceware.wordlist.asc");
    private static final InputStream POLISH_WORD_LIST_INPUT_STREAM = classLoader.getResourceAsStream("dicelist-pl.txt");
    private static final InputStream INCORRECT_CODE_FILE_INPUT_STREAM = classLoader.getResourceAsStream("incorrect_code_word_list.txt");
    private static final InputStream NOT_ENOUGH_ENTRIES_FILE_INPUT_STREAM = classLoader.getResourceAsStream("not_enough_entries_word_list.txt");

    private DicewareWordListReader reader = new DicewareWordListReader();

    @Test
    public void shouldThrowException_forFileWithIncorrectCodes() throws IOException, InvalidWordCountException {
        Throwable thrown = catchThrowable(() -> reader.read(INCORRECT_CODE_FILE_INPUT_STREAM));

        assertThat(thrown).isInstanceOf(InvalidWordCountException.class)
                .hasMessageStartingWith(DicewareWordListReader.INVALID_WORD_COUNT);
    }

    @Test
    public void shouldThrowException_forFileWithNotEnoughEntries() throws IOException, InvalidWordCountException {
        Throwable thrown = catchThrowable(() -> reader.read(NOT_ENOUGH_ENTRIES_FILE_INPUT_STREAM));

        assertThat(thrown).isInstanceOf(InvalidWordCountException.class)
                .hasMessageStartingWith(DicewareWordListReader.INVALID_WORD_COUNT);
    }

    @Test
    public void shouldReadWordList_forValidFile() throws IOException, InvalidWordCountException {
        Map<String, String> codeToWord = reader.read(VALID_FILE_INPUT_STREAM);

        assertThat(codeToWord).containsKeys("11111", "11112", "11113", "11114", "11115", "11116");
        assertThat(codeToWord).containsValues("abacus", "abdomen", "abdominal", "abide", "abiding", "ability");
    }

    @Test
    public void shouldReadWordList_forEnglishFile() throws IOException, InvalidWordCountException {
        Map<String, String> codeToWord = reader.read(ENGLISH_WORD_LIST_INPUT_STREAM);

        assertThat(codeToWord).containsKeys("11125", "11126", "11131", "11132", "11133");
        assertThat(codeToWord).containsValues("aback", "abase", "abash", "abate", "abbas");
    }

    @Test
    public void shouldReadWordList_forPolishFile() throws IOException, InvalidWordCountException {
        Map<String, String> codeToWord = reader.read(POLISH_WORD_LIST_INPUT_STREAM);

        assertThat(codeToWord).containsKeys("12661", "12662", "12663", "12664", "12665");
        assertThat(codeToWord).containsValues("arba", "arbuz", "arena", "arenga", "areszt");
    }

}