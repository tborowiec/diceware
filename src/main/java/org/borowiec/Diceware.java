package org.borowiec;

import org.borowiec.generator.DicewarePasswordGenerator;
import org.borowiec.reader.DicewareWordListFileReader;
import org.borowiec.validator.DicewareArgsValidator;

import java.io.File;
import java.util.Map;

public class Diceware {

    private final DicewareArgsValidator argsValidator;
    private final DicewareWordListFileReader wordListFileReader;
    private final DicewarePasswordGenerator passwordGenerator;

    public Diceware(DicewareArgsValidator argsValidator,
                    DicewareWordListFileReader wordListFileReader,
                    DicewarePasswordGenerator passwordGenerator) {
        this.argsValidator = argsValidator;
        this.wordListFileReader = wordListFileReader;
        this.passwordGenerator = passwordGenerator;
    }

    public static void main(String[] args) {
        Diceware diceware = new Diceware(
                new DicewareArgsValidator(),
                new DicewareWordListFileReader(),
                new DicewarePasswordGenerator()
        );

        diceware.run(args);
    }

    void run(String[] args) {
        try {
            argsValidator.validate(args);

            int minPasswordLength = Integer.parseInt(args[0]);
            File wordListFile = new File(args[1]);

            Map<String, String> codeToWord = wordListFileReader.read(wordListFile);
            String generatedPassword = passwordGenerator.generatePassword(minPasswordLength, codeToWord);

            printResult(generatedPassword);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            printUsage();
        }
    }

    private void printResult(String generatedPassword) {
        System.out.println("Generated password: " + generatedPassword);
        System.out.println("Password length: " + generatedPassword.length());
    }

    private void printUsage() {
        System.out.println("Usage: ");
        System.out.println(String.format(
                "java -jar diceware.jar %s %s",
                DicewareConstants.MIN_PASSWORD_LENGTH_PARAM_NAME,
                DicewareConstants.WORD_LIST_FILE_NAME_PARAM_NAME
        ));
        System.out.println();
        System.out.println("Parameters:");
        System.out.println(DicewareConstants.MIN_PASSWORD_LENGTH_PARAM_NAME + " - minimum length of generated password");
        System.out.println(DicewareConstants.WORD_LIST_FILE_NAME_PARAM_NAME + " - file name containing word list");
    }

}
