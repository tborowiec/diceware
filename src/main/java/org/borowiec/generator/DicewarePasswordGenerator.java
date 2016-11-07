package org.borowiec.generator;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

import static org.borowiec.DicewareConstants.CODE_DIGITS;

public class DicewarePasswordGenerator {

    private final Random random;

    public DicewarePasswordGenerator() {
        this(new SecureRandom());
    }

    public DicewarePasswordGenerator(Random random) {
        this.random = random;
    }

    public String generatePassword(int minPasswordLength, Map<String, String> codeToWord) {
        StringBuilder generatedPassword = new StringBuilder();

        do {
            String code = generateCode();
            String word = codeToWord.get(code);

            generatedPassword.append(word);
        } while (generatedPassword.length() < minPasswordLength);

        return generatedPassword.toString();
    }

    String generateCode() {
        StringBuilder generatedCode = new StringBuilder();

        for (int i = 0; i < CODE_DIGITS; ++i) {
            int codeDigit = random.nextInt(6) + 1;
            generatedCode.append(codeDigit);
        }

        return generatedCode.toString();
    }

}
