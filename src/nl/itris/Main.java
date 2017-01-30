package nl.itris;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class runs CSVReader class and handles all exceptions that are thrown by all subclasses
 * Stacktrace is enabled by default to offer debugging for developers
 * @author Yoeri Nijs
 */
public class Main {

    private static final String INTRO_TEXT = "This program needs a csv file as input file. Add it to the working directory: ";
    private static final String ASK_INPUT_FILENAME = "Enter the name of the input file (with .csv) in the working directory: ";
    private static final String FILE_NOT_FOUND = "File name is not correct or the file is already opened";
    private static final String GENERAL_EXCEPTION = "Something went wrong. Error message: ";
    private static final String GOODBYE_MESSAGE = "Done, bye!";

    // Setter for input file name
    private static String setInputFile() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);
        System.out.println(INTRO_TEXT + Common.getWorkingPath(""));
        System.out.println(ASK_INPUT_FILENAME);
        return reader.next();
    }

    /**
     * Run CSV reader
     * @param args
     */
    public static void main(String[] args) {

        try {
            // Set input file name
            String inputFile = setInputFile();

            // Set default translations for outputting project phases and codes
            CSVProperties setProperties = new CSVProperties();
            setProperties.setPhaseDefinitions();
            setProperties.setCodeDefinitions();

            // Read CSV file and write values to output file
            CSVReader csvReader = new CSVReader();
            csvReader.read(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND);
        } catch (Exception e) {
            System.out.println(GENERAL_EXCEPTION + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println(GOODBYE_MESSAGE);
        }
    }
}
