package org.borowiec.validator;

import org.borowiec.DicewareConstants;
import org.borowiec.exception.InvalidParameterException;

import java.io.File;

public class DicewareArgsValidator {

    static final String INVALID_NUMBER_OF_PARAMETERS = "Invalid number of parameters";
    static final String MIN_PASSWORD_LENGTH_NOT_A_NUMBER = DicewareConstants.MIN_PASSWORD_LENGTH_PARAM_NAME + " is not a number";
    static final String FILE_NAME_IS_EMPTY = DicewareConstants.WORD_LIST_FILE_NAME_PARAM_NAME + " is empty";
    static final String NOT_A_FILE = "Not a file: ";

    public void validate(String[] args) throws InvalidParameterException {
        if (args.length < 1 || args.length > 2) {
            throw new InvalidParameterException(INVALID_NUMBER_OF_PARAMETERS);
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(MIN_PASSWORD_LENGTH_NOT_A_NUMBER);
        }

        if (args.length == 2) {
            String wordlistFileName = args[1];
            if (wordlistFileName == null || wordlistFileName.trim().length() == 0) {
                throw new InvalidParameterException(FILE_NAME_IS_EMPTY);
            }

            File wordListFile = new File(wordlistFileName);
            if (!wordListFile.isFile()) {
                throw new InvalidParameterException(NOT_A_FILE + wordlistFileName);
            }
        }
    }

}
