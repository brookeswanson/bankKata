package net.bswanson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoryOneTest {
    private AccountNumber accountNumber;
    private AccountNumber accountNumber2;
    private ProcessAccountNumbers processAccountNumbers;
    private String[] accountNumberCharacters;
    private String[] accountNumberCharacters2;

    @Before
    public void setUp() throws Exception {
        accountNumberCharacters = new String[]{MachineCharacter.ONE.getMachineCharacter(),
                MachineCharacter.TWO.getMachineCharacter(),
                MachineCharacter.THREE.getMachineCharacter(),
                MachineCharacter.FOUR.getMachineCharacter(),
                MachineCharacter.FIVE.getMachineCharacter(),
                MachineCharacter.SIX.getMachineCharacter(),
                MachineCharacter.SEVEN.getMachineCharacter(),
                MachineCharacter.EIGHT.getMachineCharacter(),
                MachineCharacter.NINE.getMachineCharacter()};
        accountNumberCharacters2 = new String[]{MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter(),
                MachineCharacter.ZERO.getMachineCharacter()};
        accountNumber = new AccountNumber(accountNumberCharacters);
        accountNumber2 = new AccountNumber(accountNumberCharacters2);
        processAccountNumbers = new ProcessAccountNumbers();
    }

    @Test
    public void getAccountNumberStringTest() {
        String accountNumberString = accountNumber.getAccountNumberString(accountNumberCharacters);

        assertEquals("The account number string did not match the characters", accountNumberString, "123456789");
        assertNotEquals("The account string matched the wrong characters", accountNumberString, "000000000");
    }

    @Test
    public void createAccountNumberTest() throws Exception {
        String characters =   " _  _  _  _  _  _  _  _  _ "
                            + "| || || || || || || || || |"
                            + "|_||_||_||_||_||_||_||_||_|";
        AccountNumber number = processAccountNumbers.createAccountNumber(characters);
        String[] accountNumberArray = number.getAccountNumberArray();
        assertNotSame("The two account numbers are the same object", accountNumberArray, accountNumberCharacters2);
        assertEquals("The account numbers string is not 000000000", number.getAccountNumberString(accountNumberArray), "000000000");
    }

}
