package org.borowiec.reader;

import org.borowiec.DicewareConstants;
import org.borowiec.exception.InvalidWordCountException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DicewareWordListFileReader {

    static final String INVALID_WORD_COUNT = "Word list should contain " + DicewareConstants.WORD_COUNT + " words - instead contains ";

    public Map<String, String> read(File wordListFile) throws IOException, InvalidWordCountException {
        BufferedReader reader = new BufferedReader(
                new FileReader(wordListFile)
        );
        Map<String, String> codeToWord = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\s");

            String code = split[0];
            String word = split[1];

            codeToWord.put(code, word);
        }

        if (codeToWord.size() != DicewareConstants.WORD_COUNT) {
            throw new InvalidWordCountException(INVALID_WORD_COUNT + codeToWord.size());
        }

        return codeToWord;
    }

}
