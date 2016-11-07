package org.borowiec;

import java.util.regex.Pattern;

public final class DicewareConstants {

    public static final String MIN_PASSWORD_LENGTH_PARAM_NAME = "min_password_length";
    public static final String WORD_LIST_FILE_NAME_PARAM_NAME = "word_list_file_name";

    public static final int WORD_COUNT = 7776;
    public static final int CODE_DIGITS = 5;

    public static final Pattern codePattern = Pattern.compile("^[1-6]{" + CODE_DIGITS + "}");

    private DicewareConstants() {
        /* intentionally blank */
    }

}
