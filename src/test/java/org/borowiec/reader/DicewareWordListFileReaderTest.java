package org.borowiec.reader;

import org.borowiec.exception.InvalidWordCountException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DicewareWordListFileReaderTest {

    private static final ClassLoader classLoader = DicewareWordListFileReaderTest.class.getClassLoader();

    private static final String VALID_FILE_NAME = classLoader.getResource("eff_large_wordlist.txt").getPath();
    private static final String INVALID_FILE_NAME = classLoader.getResource("example_word_list.txt").getPath();

    private DicewareWordListFileReader reader = new DicewareWordListFileReader();

    @Test
    public void shouldThrowException_forInvalidFile() throws IOException, InvalidWordCountException {
        Throwable thrown = catchThrowable(() -> reader.read(new File(INVALID_FILE_NAME)));

        assertThat(thrown).isInstanceOf(InvalidWordCountException.class)
                .hasMessageStartingWith(DicewareWordListFileReader.INVALID_WORD_COUNT);
    }

    @Test
    public void shouldReadWordList_forExistingFile() throws IOException, InvalidWordCountException {
        Map<String, String> codeToWord = reader.read(new File(VALID_FILE_NAME));

        assertThat(codeToWord).containsKeys("11111", "11112", "11113", "11114", "11115", "11116");
        assertThat(codeToWord).containsValues("abacus", "abdomen", "abdominal", "abide", "abiding", "ability");
    }

}