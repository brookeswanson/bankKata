package net.bswanson;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserStoryTwoTest {
    private AccountNumber accountNumber;

    @Before
    public void setUp() throws Exception {
        accountNumber = new AccountNumber(new String[1]);
    }

    @Test
    public void checkSumTest() {
        String accountNumberString = "000000000";
        String accountNumberString2 = "111111111";
        Boolean checkSum = accountNumber.checkSum(accountNumberString);
        Boolean checkSum2 = accountNumber.checkSum(accountNumberString2);

        assertFalse("The checksum returned true for case:111111111", checkSum2);
        assertTrue("The checksum returned false for case:000000000", checkSum);
    }


}
