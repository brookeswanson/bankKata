package net.bswanson;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * The type Process account numbers.
 */
class ProcessAccountNumbers {
    private final Logger log = Logger.getLogger(this.getClass());
    private AccountNumbers accountNumbers;


    /**
     * Instantiates a new Process account numbers.
     */
    public ProcessAccountNumbers() {
        accountNumbers = new AccountNumbers();
    }

    /**
     * Run.
     *
     * @param args the args
     */
    public void run(String[] args) {
        String defaultFileToRead = "/Users/brookeswanson/Desktop/defaultReadFile.txt";
        String defaultFileToWrite = System.getProperty("java.io.tmpdir") + "convertedAccountNumbers";

        if (args.length == 1) {
            defaultFileToRead = args[0];
            log.info("Reading from " + args[0]);
        } else if (args.length == 2) {
            defaultFileToRead = args[0];
            defaultFileToWrite = args[1];
            log.info("Reading from " + args[0] + " Writing to " + args[1]);
        }

        openFileToRead(defaultFileToRead);
        accountNumbers.writeOutputFile(defaultFileToWrite);

    }

    /**
     * Read lines in file.
     *
     * @param input the input
     * @throws IOException the io exception
     */
    void readLinesInFile(BufferedReader input) throws IOException {
        String line;

        while (input.ready()) {
            // in a well formatted document, with these sets of characters numbers consisting of only 1 or 4 can read incorrectly
            line = input.readLine();
            if (line.isEmpty()) {
                line += Strings.repeat(" ", 27); // empty rows for 1 & 4's weren't always read
            }
            line += input.readLine() + input.readLine();

            if (line.length() == 81) {
                accountNumbers.addAccountNumber(createAccountNumber(line));
            }

            // skip line 4
            input.readLine();
        }
    }

    /**
     * Create account number object.
     *
     * @param characters the input from the machine printed characters in a string.
     */
    AccountNumber createAccountNumber(String characters) {
        String[] splitCharacters = new String[]{"", "", "", "", "", "", "", "", ""};
        Iterable<String> machineCharacters = Splitter.fixedLength(3).split(characters);

        int characterLocation = 0;
        for (String character: machineCharacters) {
            splitCharacters[characterLocation] += character;

            characterLocation += 1;

            if (characterLocation % 9 == 0) {
                characterLocation = 0;
            }
        }

        return new AccountNumber(splitCharacters);

    }


    /**
     * Open file to read.
     *
     * @param inputFilePath the input file path
     */
    void openFileToRead(String inputFilePath) {
        try (BufferedReader input = new BufferedReader(new FileReader(inputFilePath))
        ) {
            readLinesInFile(input);
        } catch (FileNotFoundException fileNotFoundException) {
            log.error("The file " + inputFilePath + " was not found.", fileNotFoundException);
        } catch (IOException ioException) {
            log.error("There was an error that occurred when attempting to read the file. ", ioException);
        } catch (Exception exception) {
            log.error("");
        }
    }
}
