package net.bswanson;


import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.IntStream;

import static net.bswanson.StringUtilities.getOtherCharacters;
import static net.bswanson.StringUtilities.replaceCharacterInString;

/**
 * The type Account Number which represents one account printed out by the Bank Machine.
 *
 * @author bswanson
 */
public class AccountNumber {
    private String[] accountNumberArray;
    private final Logger log = Logger.getLogger(this.getClass());

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
        return Arrays.stream(characterArray).map(StringUtilities::getStringFromCharacter).reduce("", String::concat);
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
            validAccounts = loopThroughCharacters(accountNumberArray, machineIndex, accountStringCharacters, index);
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
    static List<String> loopThroughCharacters(String[] accountNumberArray, int machineIndex, String accountString, int index) {
        List<String> validAccounts = new ArrayList<String>();
        String machineCharacter = accountNumberArray[index];

        Iterator<Character> options = getOtherCharacters(Character.toString(machineCharacter.charAt(machineIndex)));

        while (options.hasNext()) {

            String checkCharacter = createNewCharacter(machineIndex, machineCharacter, options.next());
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
     * Create new character string.
     *
     * @param index        the index
     * @param input        the input
     * @param tryCharacter the try character
     * @return the string
     */
    static String createNewCharacter(int index, String input, char tryCharacter) {
        String character = replaceCharacterInString(index, input, tryCharacter);
        return StringUtilities.getStringFromCharacter(character);
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
    static Boolean checkSum(String checkString) {
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
