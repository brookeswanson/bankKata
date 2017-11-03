package net.bswanson;
import com.google.common.base.Splitter;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;


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
        String[] readFiles = {"/Users/brookeswanson/Desktop/defaultReadFile.txt", "/Users/brookeswanson/Desktop/defaultReadFile.txt"};
        IntStream.range(0, args.length)
                .filter(i -> i < readFiles.length)
                .mapToObj(i -> readFiles[i]);

        try {
            openFileToRead(readFiles[0]);
        } catch (IOException io) {
            log.error("The file was not able to read", io);
        }
        accountNumbers.writeOutputFile(readFiles[1]);

    }

    /**
     * Read lines in file.
     *
     * @param lines the lines
     * @throws IOException the io exception
     */
    void readLinesInFile(Stream<String> lines) throws IOException {
        StringBuilder sb = new StringBuilder();
        final Integer[] index = {0};
        Splitter.fixedLength(81).split(lines.filter(line -> !((index[0] += 1) % 4 == 0))
                .reduce("", (a, b) ->  a + b))
                .iterator()
                .forEachRemaining(ch -> accountNumbers.addAccountNumber(createAccountNumber(ch)));
    }

    /**
     * Create account number object.
     *
     * @param characters the input from the machine printed characters in a string.
     * @return the account number
     */
    AccountNumber createAccountNumber(String characters) {
        String[] splitCharacters = new String[]{"", "", "", "", "", "", "", "", ""};
        Iterable<String> machineCharacters = Splitter.fixedLength(3).split(characters);

        final Integer[] characterLocation = {0};
        Splitter.fixedLength(3).split(characters).iterator().forEachRemaining(ch ->splitCharacters[(characterLocation[0] += 1) % 9] += ch);

        return new AccountNumber(splitCharacters);

    }


    /**
     * Open file to read.
     *
     * @param inputFilePath the input file path
     * @throws IOException the io exception
     */
    void openFileToRead(String inputFilePath) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(inputFilePath));
        readLinesInFile(lines);
    }
}
