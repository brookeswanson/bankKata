package net.bswanson;

/**
 * The enum Machine character.
 */
public enum MachineCharacter {
    /**
     * Zero machine character.
     */
    ZERO (
              " _ "
            + "| |"
            + "|_|",
            "0",
            0
    ),
    /**
     * One machine character.
     */
    ONE (
             "   "
            + "  |"
            + "  |",
            "1",
            1
    ),
    /**
     * Two machine character.
     */
    TWO (
              " _ "
            + " _|"
            + "|_ ",
            "2",
            2
    ),
    /**
     * Three machine character.
     */
    THREE (
              " _ "
            + " _|"
            + " _|",
            "3",
            3
    ),
    /**
     * Four machine character.
     */
    FOUR (
              "   "
            + "|_|"
            + "  |",
            "4",
            4
    ),
    /**
     * Five machine character.
     */
    FIVE (
              " _ "
            + "|_ "
            + " _|",
            "5",
            5
    ),
    /**
     * Six machine character.
     */
    SIX (
              " _ "
            + "|_ "
            + "|_|",
            "6",
            6
    ),
    /**
     * Seven machine character.
     */
    SEVEN (
              " _ "
            + "  |"
            + "  |",
            "7",
            7
    ),
    /**
     * Eight machine character.
     */
    EIGHT (
              " _ "
            + "|_|"
            + "|_|",
            "8",
            8

    ),
    /**
     * Nine machine character.
     */
    NINE (
              " _ "
            + "|_|"
            + " _|",
            "9",
            9
    );

    private final String character;   // represents the machine printed number
    private final String stringNumber; // The basic string representation of the number
    private final int intNumber; // the int (for checksum computation)

    /**
     *
     * @param character
     * @param stringNumber
     * @param intNumber
     */
    MachineCharacter(String character, String stringNumber, int intNumber) {
        this.character = character;
        this.stringNumber = stringNumber;
        this.intNumber = intNumber;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    int getNumber() {
        return intNumber;
    }

    /**
     * Gets character.
     *
     * @return the character
     */
    String getCharacter() {
        return stringNumber;
    }


    /**
     * Gets machine character.
     *
     * @return the machine character
     */
    String getMachineCharacter() {
        return character;
    }

    /**
     * Check character boolean.
     *
     * @param characterToCheck the character to check
     * @return the boolean
     */
    Boolean checkCharacter(String characterToCheck) {
        return character.equals(characterToCheck);
    }
}
