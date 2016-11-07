package org.borowiec.reader;

import org.borowiec.DicewareConstants;
import org.borowiec.exception.InvalidWordCountException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.borowiec.DicewareConstants.codePattern;

public class DicewareWordListReader {

    static final String INVALID_WORD_COUNT = "Word list should contain " + DicewareConstants.WORD_COUNT + " words - instead contains ";

    public Map<String, String> read(String fileName) throws IOException, InvalidWordCountException {
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        return read(fileInputStream);
    }

    public Map<String, String> read(InputStream wordListStream) throws IOException, InvalidWordCountException {
        Map<String, String> codeToWord = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(wordListStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (lineMatches(line)) {
                    String[] split = line.split("\\s");

                    String code = split[0];
                    String word = split[1];

                    codeToWord.put(code, word);
                }
            }
        }

        if (codeToWord.size() != DicewareConstants.WORD_COUNT) {
            throw new InvalidWordCountException(INVALID_WORD_COUNT + codeToWord.size());
        }

        return codeToWord;
    }

    private boolean lineMatches(String line) {
        return codePattern.matcher(line).find();
    }

}
