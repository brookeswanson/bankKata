package net.bswanson;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The account numbers hold a list of account number objects that are read from the bank machine output.
 * Account Numbers can then take that list and write it to a default file location, or a location specified through
 * command line arguments.
 *
 * @author bswanson
 */
public class AccountNumbers {
    private final Logger log = Logger.getLogger(this.getClass());

    private List<AccountNumber> accountNumbers;

    /**
     * Instantiates a new Account numbers.
     */
    public AccountNumbers() {
        accountNumbers = new ArrayList<AccountNumber>();
    }

    /**
     * Add account number.
     *
     * @param accountNumber the account number
     */
    protected void addAccountNumber(AccountNumber accountNumber) {
        accountNumbers.add(accountNumber);
    }

    /**
     * Write account numbers.
     *
     * @param outputFile the output file
     */
    public void writeAccountNumbers(PrintWriter outputFile) {
        for (AccountNumber accountNumber : accountNumbers) {
            outputFile.println(accountNumber);
        }
    }

    /**
     * Write output file.
     *
     * @param outputFilePath the output file path
     */
    public void writeOutputFile(String outputFilePath) {
        try (PrintWriter output
                     = new PrintWriter(new BufferedWriter
                (new FileWriter(outputFilePath)))) {
            writeAccountNumbers(output);
        } catch (FileNotFoundException fileNotFound) {
            log.error("File Was Not Found", fileNotFound);
        } catch (IOException ioException) {
            log.error("Error Reading File", ioException);
        } catch (Exception exception) {
            log.error("A fatal error occured",  exception);
        }
    }

}
