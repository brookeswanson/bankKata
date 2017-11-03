package net.bswanson;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtilities {
    static Iterator<Character> getOtherCharacters(String removeCharacter) {
         return "_ |".chars().mapToObj(c -> (char)c).filter(c -> !(c == removeCharacter.charAt(0))).iterator();
    }

    /**
     * Iterates through the the Machine Character Representations of the account numbers.
     *
     * @param machineCharacter the character the machine printed
     * @return a digit [0-9] or ? depending on if the passed in string matched a value.
     */
    static String getStringFromCharacter(String machineCharacter) {
        // Check for a match between all characters
        return Arrays
                .stream(MachineCharacter.values())
                .filter(ch -> ch.checkCharacter(machineCharacter))
                .findFirst()
                .map(ch -> ch.getCharacter().isEmpty() ? "?" : ch.getCharacter()).toString();
    }

    /**
     * Replace character in string string.
     *
     * @param index        the index
     * @param input        the input
     * @param tryCharacter the try character
     * @return the string
     */
    static String replaceCharacterInString(int index, String input, char tryCharacter) {
        StringBuilder sb = new StringBuilder();
        sb.append(input.substring(0, index));
        sb.append(tryCharacter);
        sb.append(input.substring(index +1));

        return sb.toString();
    }
}
