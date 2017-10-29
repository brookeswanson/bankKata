package net.bswanson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoryThreeTest {
    private AccountNumber accountNumber;

    @Before
    public void setUp() throws Exception {
        accountNumber = new AccountNumber(new String[1]);
    }

    @Test
    public void addDescriptionToAccountTest() {
        String emptyString = accountNumber.addDescriptionToAccount("000000000");
        String ERR = accountNumber.addDescriptionToAccount("111111111");
        String returnedString = accountNumber.getStringFromCharacter("a");
        String ILL = accountNumber.addDescriptionToAccount("12?333333");

        assertEquals("The valid account number returned ILL or ERR", emptyString, "");
        assertEquals("The character was not converted correctly", "?", returnedString);
        assertEquals("The account number that failed checksum returned ILL or ''", " ERR", ERR);
        assertEquals("The account number containing a ? returned ERR or ''", " ILL", ILL);

    }


}
