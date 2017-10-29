package net.bswanson;

/**
 * This Application serves as a solution to the coding challenge of parsing through printouts of
 */
public class App
{
    /**
     * The entry point of the application.
     *
     * @param args the input arguments
     */
    public static void main( String[] args ) {
        ProcessAccountNumbers processAccountNumbers = new ProcessAccountNumbers();
        processAccountNumbers.run(args);
    }
}
