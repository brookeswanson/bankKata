package net.bswanson;


/**
 * The type Account Number which represents one account printed out by the Bank Machine.
 *
 * @author bswanson
 */
public class AccountNumber {
    private String[] accountNumberArray;

    /**
     * Instantiates a new Account number.
     *
     * @param accountNumberArray the account number array
     */
    public AccountNumber(String[] accountNumberArray) {
        this.accountNumberArray = accountNumberArray;
    }

    /**
     * Get account number array string [ ].
     *
     * @return the string [ ]
     */
    String[] getAccountNumberArray() {
        return accountNumberArray;
    }

    /**
     * Iterates through the account number characters in the provided array of machine characters.
     * This parses the characters into both an array list of integers and a string.
     *
     * @param characterArray the character array
     * @return the account number string
     */
    String getAccountNumberString(String[] characterArray) {
        StringBuilder accountNumberString = new StringBuilder();

        for (String accountNumber : characterArray) {
            accountNumberString.append(getStringFromCharacter(accountNumber));
        }

        return accountNumberString.toString();
    }

    /**
     * Iterates through the the Machine Character Representations of the account numbers.
     *
     * @param machineCharacter the character the machine printed
     * @return a digit [0-9] or ? depending on if the passed in string matched a value.
     */
    String getStringFromCharacter(String machineCharacter) {
        // Check for a match between all characters
        for (MachineCharacter number: MachineCharacter.values()) {

            // Match is found
            if (number.checkCharacter(machineCharacter)) {
                // Add the number to the array
                return number.getCharacter();
            }
        }
        // No match was found indicating an illegal character
        return "?";
    }

    /**
     * Adds, ILL, or ERR to the accountNumber String
     *
     * @return the type of account number.
     */
    String addDescriptionToAccount(String accountString) {
        // There will be fewer items in the account number int array with invalid characters
        if (accountString.contains("?")) {
            return " ILL";
        } else if (checkSum(accountString)) {
            return "";
        } else {
            return " ERR";
        }
    }


    /**
     * This checks the numbers against the
     * @return valid or not valid account number
     */
    Boolean checkSum(String checkString) {
        int sum = 0;

        for (int index = 0; index < checkString.length(); index += 1) {
            String character = "" + checkString.charAt(index);
            int value = Integer.parseInt(character);
            sum += value * -(index - 9);
        }

        // Returns True if the account is valid, and false otherwise.
        return sum % 11 == 0;
    }

    /**
     * Takes the string by converting the original character array and adds a description.
     * @return the string representation of the class
     */
    public String toString() {
        String accountNumberString = getAccountNumberString(accountNumberArray);
        return accountNumberString + addDescriptionToAccount(accountNumberString);
    }
}
