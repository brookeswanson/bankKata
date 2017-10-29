package net.bswanson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MachineCharacterTest {
    private static MachineCharacter machineCharacter;

    @Test
    public void getNumber() throws Exception {
        int one = machineCharacter.ONE.getNumber();

        assertEquals("One was not returned", 1 , one);
    }

    @Test
    public void getCharacter() throws Exception {
        String two = machineCharacter.TWO.getCharacter();
        assertEquals("The character 2 was not returned", "2", two);
    }

    @Test
    public void getMachineCharacter() {
        String four = machineCharacter.FOUR.getMachineCharacter();
        String fourCharacter =  "   "
                              + "|_|"
                              + "  |";
        assertEquals("The machine character representation of the number 4", four, fourCharacter);
    }
    @Test
    public void checkCharacter() throws Exception {
        String twoCharacterRepresentation = " _ "
                                          + " _|"
                                          + "|_ ";
        String notACharacter = "___!";
        assertTrue("The two did not match the enum character", machineCharacter.TWO.checkCharacter(twoCharacterRepresentation));
        assertFalse("The characters should not match", machineCharacter.THREE.checkCharacter(notACharacter));
    }

}