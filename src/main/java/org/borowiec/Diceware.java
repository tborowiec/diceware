package org.borowiec;

import org.borowiec.exception.InvalidParameterException;
import org.borowiec.exception.InvalidWordCountException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Diceware {

    private static final String MIN_PASSWORD_LENGTH_PARAM_NAME = "min_password_length";
    private static final String WORD_LIST_FILE_NAME_PARAM_NAME = "word_list_file_name";

    private static final int WORD_COUNT = 7776;
    static final int CODE_DIGITS = 5;

    private static final Random random = new SecureRandom();

    static final String INVALID_NUMBER_OF_PARAMETERS = "Invalid number of parameters";
    static final String MIN_PASSWORD_LENGTH_NOT_A_NUMBER = MIN_PASSWORD_LENGTH_PARAM_NAME + " is not a number";
    static final String FILE_NAME_IS_EMPTY = WORD_LIST_FILE_NAME_PARAM_NAME + " is empty";
    static final String NOT_A_FILE = "Not a file: ";
    static final String INVALID_WORD_COUNT = "Word list should contain " + WORD_COUNT + " words - instead contains ";

    int minPasswordLength;
    File wordListFile;

    final Map<String, String> codeToWord = new HashMap<>();

    public static void main(String[] args) {
        new Diceware().run(args);
    }

    void run(String[] args) {
        try {
            validateAndAssignInputParameters(args);
            readWordList();
            String generatedPassword = generatePassword();
            printResult(generatedPassword);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            printUsage();
        }
    }

    void validateAndAssignInputParameters(String[] args) throws InvalidParameterException {
        if (args.length != 2) {
            throw new InvalidParameterException(INVALID_NUMBER_OF_PARAMETERS);
        }

        try {
            minPasswordLength = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(MIN_PASSWORD_LENGTH_NOT_A_NUMBER);
        }

        String wordlistFileName = args[1];
        if (wordlistFileName == null || wordlistFileName.trim().length() == 0) {
            throw new InvalidParameterException(FILE_NAME_IS_EMPTY);
        }

        wordListFile = new File(wordlistFileName);
        if (!wordListFile.isFile()) {
            throw new InvalidParameterException(NOT_A_FILE + wordlistFileName);
        }
    }


    void readWordList() throws IOException, InvalidWordCountException {
        BufferedReader reader = new BufferedReader(
                new FileReader(wordListFile)
        );

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\s");

            String code = split[0];
            String word = split[1];

            codeToWord.put(code, word);
        }

        if (codeToWord.size() != 7776) {
            throw new InvalidWordCountException(INVALID_WORD_COUNT + codeToWord.size());
        }
    }

    private String generatePassword() {
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

    private void printResult(String generatedPassword) {
        System.out.println("Generated password: " + generatedPassword);
        System.out.println("Password length: " + generatedPassword.length());
    }

    private void printUsage() {
        System.out.println("Usage: ");
        System.out.println(String.format(
                "java -jar diceware.jar %s %s", MIN_PASSWORD_LENGTH_PARAM_NAME, WORD_LIST_FILE_NAME_PARAM_NAME)
        );
        System.out.println();
        System.out.println("Parameters:");
        System.out.println(MIN_PASSWORD_LENGTH_PARAM_NAME + " - minimum length of generated password");
        System.out.println(WORD_LIST_FILE_NAME_PARAM_NAME + " - file name containing word list");
    }

}
