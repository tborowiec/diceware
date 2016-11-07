package org.borowiec;

import org.borowiec.generator.DicewarePasswordGenerator;
import org.borowiec.reader.DicewareWordListReader;
import org.borowiec.validator.DicewareArgsValidator;

import java.io.InputStream;
import java.util.Map;

public class Diceware {

    static final InputStream DEFAULT_WORD_LIST_INPUT_STREAM =
            Diceware.class.getClassLoader().getResourceAsStream("eff_large_wordlist.txt");

    private final DicewareArgsValidator argsValidator;
    private final DicewareWordListReader wordListReader;
    private final DicewarePasswordGenerator passwordGenerator;

    public Diceware(DicewareArgsValidator argsValidator,
                    DicewareWordListReader wordListReader,
                    DicewarePasswordGenerator passwordGenerator) {
        this.argsValidator = argsValidator;
        this.wordListReader = wordListReader;
        this.passwordGenerator = passwordGenerator;
    }

    public static void main(String[] args) {
        Diceware diceware = new Diceware(
                new DicewareArgsValidator(),
                new DicewareWordListReader(),
                new DicewarePasswordGenerator()
        );

        diceware.run(args);
    }

    void run(String[] args) {
        try {
            argsValidator.validate(args);

            int minPasswordLength = Integer.parseInt(args[0]);

            Map<String, String> codeToWord;
            if (args.length == 2) {
                codeToWord = wordListReader.read(args[1]);
            } else {
                codeToWord = wordListReader.read(DEFAULT_WORD_LIST_INPUT_STREAM);
            }

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
                "java -jar diceware.jar %s [%s]",
                DicewareConstants.MIN_PASSWORD_LENGTH_PARAM_NAME,
                DicewareConstants.WORD_LIST_FILE_NAME_PARAM_NAME
        ));
        System.out.println();
        System.out.println("Parameters:");
        System.out.println(DicewareConstants.MIN_PASSWORD_LENGTH_PARAM_NAME + " - minimum length of generated password");
        System.out.println(DicewareConstants.WORD_LIST_FILE_NAME_PARAM_NAME + " - (optional) file name containing word list; " +
                "if not provided - default list will be used");
    }

}
