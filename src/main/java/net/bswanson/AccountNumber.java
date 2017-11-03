package net.bswanson;


import org.apache.log4j.Logger;

import javax.crypto.Mac;
import java.util.*;
import java.util.stream.IntStream;

/**
 * The type Account Number which represents one account printed out by the Bank Machine.
 *
 * @author bswanson
 */
public class AccountNumber {
    private String[] accountNumberArray;
    private Map<Character, char[]> characterMap;
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Instantiates a new Account number.
     *
     * @param accountNumberArray the account number array
     */
    public AccountNumber(String[] accountNumberArray) {
        this.accountNumberArray = accountNumberArray;
        char[] pipeCharacters = " _".toCharArray();
        char[] spaceCharacters = "|_".toCharArray();
        char[] underscoreCharacters = " |".toCharArray();

        characterMap = new HashMap<Character, char[]>();
        characterMap.put(' ', spaceCharacters);
        characterMap.put('_', underscoreCharacters);
        characterMap.put('|', pipeCharacters);

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
        return Arrays.stream(characterArray).map(AccountNumber::getStringFromCharacter).reduce("", String::concat);
    }

    /**
     * Iterates through the the Machine Character Representations of the account numbers.
     *
     * @param machineCharacter the character the machine printed
     * @return a digit [0-9] or ? depending on if the passed in string matched a value.
     */
    static String getStringFromCharacter(String machineCharacter) {
        // Check for a match between all characters
        Arrays.stream(MachineCharacter.values()).filter(ch -> ch.checkCharacter(machineCharacter));
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
     * @param accountString the account string
     * @return the type of account number.
     */
    String addDescriptionToAccount(String accountString) {
        List<String> validAccounts;

        // There will be a question mark in the invalid characters
        if (accountString.matches("//?.*//?")) { // catches more than 1 invalid character
            return accountString + " ILL";
        } else if (accountString.contains("?")){
            int index = accountString.indexOf("?");
            validAccounts = possibleAccountNumbers(accountString, index);

            return getAmbListOfAccountNumbers(validAccounts, accountString, " ILL");
        } else if (checkSum(accountString)) {
            return accountString;
        } else {
            validAccounts = iterateOverPossibilites(accountString);

            return getAmbListOfAccountNumbers(validAccounts, accountString, " ERR");
        }
    }

    /**
     * Iterate over possibilites.
     *
     * @param accountNumberString the account number string
     * @return the list
     */
    List<String> iterateOverPossibilites(String accountNumberString) {
        List<String> validAccounts = new ArrayList<>();

        for (int index = 0; index < accountNumberString.length(); index += 1)
        {
            validAccounts = possibleAccountNumbers(accountNumberString, index);
        }

        return validAccounts;
    }

    /**
     * Possible account numbers string.
     *
     * @param accountStringCharacters the account string characters
     * @param index                   the index
     * @return the string
     */
    List<String> possibleAccountNumbers(String accountStringCharacters, int index) {
        List<String> validAccounts = new ArrayList<String>();
        String machineCharacter = accountNumberArray[index];
        for (int machineIndex = 0; machineIndex < machineCharacter.length(); machineIndex += 1) {
            validAccounts = loopThroughCharacters(machineIndex, accountStringCharacters, index);
        }

        return validAccounts;
    }

    /**
     * Loop through characters list.
     *
     * @param machineIndex  the machine index
     * @param accountString the account string
     * @param index         the index
     * @return the list
     */
    List<String> loopThroughCharacters(int machineIndex, String accountString, int index) {
        List<String> validAccounts = new ArrayList<String>();
        String machineCharacter = accountNumberArray[index];

        char character = machineCharacter.charAt(machineIndex);
        char[] options = characterMap.get(character);

        for (char option :
                options) {

            String checkCharacter = createNewCharacter(machineIndex, machineCharacter, option);
            if (!checkCharacter.equals("?")) {
                String accountStringCharactersString = replaceCharacterInString(index, accountString, checkCharacter.charAt(0));
                if (checkSum(accountStringCharactersString)) {
                    validAccounts.add(accountStringCharactersString);
                }
            }
        }

        return validAccounts;
    }

    /**
     * Replace character in string string.
     *
     * @param index        the index
     * @param input        the input
     * @param tryCharacter the try character
     * @return the string
     */
    String replaceCharacterInString(int index, String input, char tryCharacter) {
        StringBuilder sb = new StringBuilder();
        sb.append(input.substring(0, index));
        sb.append(tryCharacter);
        sb.append(input.substring(index +1));

        return sb.toString();
    }

    /**
     * Create new character string.
     *
     * @param index        the index
     * @param input        the input
     * @param tryCharacter the try character
     * @return the string
     */
    String createNewCharacter(int index, String input, char tryCharacter) {
        String character = replaceCharacterInString(index, input, tryCharacter);
        return getStringFromCharacter(character);
    };

    /**
     * Gets amb list of account numbers.
     *
     * @param validAccounts    the valid accounts
     * @param oldAccountNumber the old account number
     * @param appendErrOrIll   the append err or ill
     * @return the amb list of account numbers
     */
    String getAmbListOfAccountNumbers(List<String> validAccounts, String oldAccountNumber, String appendErrOrIll) {
        if (validAccounts.size() == 0) {
            return oldAccountNumber + appendErrOrIll;
        } else if (validAccounts.size() == 1) {
            return validAccounts.get(0);
        } else {
            String prefix = "";
            StringBuilder sb = new StringBuilder();
            sb.append(oldAccountNumber);
            sb.append(" AMB ");
            sb.append("[");
            for (String account: validAccounts) {
                sb.append(prefix);
                prefix = ", ";
                sb.append("'");
                sb.append(account);
                sb.append("'");
            }
            sb.append("]");
            return sb.toString();
        }

    }

    /**
     * This checks the numbers against the
     *
     * @param checkString the check string
     * @return valid or not valid account number
     */
    Boolean checkSum(String checkString) {
        return IntStream.range(0, checkString.length())
                .map(i -> Integer.parseInt(checkString.charAt(i) + "") * -(i - 9))
                .sum() % 11 == 0;
    }

    /**
     * Takes the string by converting the original character array and adds a description.
     * @return the string representation of the class
     */
    public String toString() {
        String accountNumberString = getAccountNumberString(accountNumberArray);
        return addDescriptionToAccount(accountNumberString);
    }
}
