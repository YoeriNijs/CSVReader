package nl.itris;

import java.io.*;
import java.util.*;

/**
 * This class reads input from a csv file
 * @author Yoeri Nijs
 */
public class CSVReader {

    private HashMap<String, String> afasCodes = new HashMap<>();
    private HashMap<String, String> employeeCodes = new HashMap<>();
    private CSVWriter csvWriter;

    private static final String CSV_CODES_FILE = "AfasCodes.txt";
    private static final String EMPLOYEE_CODES_FILE = "EmployeeCodes.txt";
    private static final String AFAS_CODES = "afasCodes";
    private static final String EMPLOYEE_CODES = "employeeCodes";
    private static final String ASK_AFAS_CODE = "Enter the AFAS code for ";
    private static final String ASK_EMPLOYEE_CODE = "Enter the employee code for ";
    private static final String ASK_PREFIX = "Enter custom prefix for output file(s): ";

    public CSVReader() throws Exception {
        csvWriter = new CSVWriter();
    }

    /**
     * Read CSV file and write output file
     * @param inputFile
     * @throws Exception
     */
    public void read(String inputFile) throws Exception {

        // Ask for savename
        outputFileName();

        // Create and read AFAS codes
        try (BufferedReader brCodes = new BufferedReader(new FileReader(Common.getWorkingPath(inputFile)))) {
            int count = 0;
            String lineCodes = "";
            while((lineCodes = brCodes.readLine()) != null) {
                String[] output = lineCodes.split(",");
                if(count != 0 && output.length >= 10) {
                    connectCode(output[5], AFAS_CODES);
                    connectCode(output[9], EMPLOYEE_CODES);
                }
                count++;
            }
        }

        // Save AFAS codes
        save(AFAS_CODES);

        // Save employee codes
        save(EMPLOYEE_CODES);

        // Read rows and store them
        try (BufferedReader brRows = new BufferedReader(new FileReader(Common.getWorkingPath(inputFile)))) {
            int count = 0;
            String lineRows = "";
            while((lineRows = brRows.readLine()) != null) {
                String[] output = lineRows.split(",");
                if(count == 0) {
                    header();
                } else {
                    row(output);
                }
                count++;
            }
        }
    }

    /**
     * Method for writing header
     * @throws Exception General exception
     */
    private void header() throws Exception {
        csvWriter.WriteHeader();
    }

    // Method for writing row
    private void row(String[] row) throws Exception {
        csvWriter.writeRow(row);
    }

    // Method for creating hashmap
    private void connectCode(String key, String task) throws Exception {

        String fileToSave;
        String message;
        fileToSave = message = "";
        HashMap<String, String> hashMap = new HashMap<>();

        // Switch for different hashmaps
        switch (task) {
            case AFAS_CODES:
                fileToSave = CSV_CODES_FILE;
                hashMap = afasCodes;
                message = ASK_AFAS_CODE;
                break;
            case EMPLOYEE_CODES:
                fileToSave = EMPLOYEE_CODES_FILE;
                hashMap = employeeCodes;
                message = ASK_EMPLOYEE_CODE;
                break;
            default:
                break;
        }

        // Prevent double quotes in code
        key = key.replace("\"", "");

        // If file with stored codes exists, add codes to hashmap
        File file = new File(Common.getWorkingPath(fileToSave));
        if (file.length() != 0) {

            // Clean file
            String storedCodes = readFile(fileToSave);
            storedCodes = storedCodes.replace("{", "");
            storedCodes = storedCodes.replace("}", "");
            storedCodes = storedCodes.replace("\"", "");

            // Save key and value to local hashmap
            for (String retval: storedCodes.split(",")) {
                String[] split = retval.split("=");
                hashMap.put(split[0].trim(), split[1].trim());
            }
        }

        // If not found, ask for user translation
        if (hashMap.get(key) == null) {
            Scanner reader = new Scanner(System.in);
            System.out.println(message + key + ": ");
            String translation = reader.next();
            hashMap.put(key, translation);
        }
    }

    // Setup for output file name
    private void outputFileName() throws Exception {

        // Get current working path
        String saveName = Common.getWorkingPath(csvWriter.getSaveNameFile());

        // Create output file if it does not exist yet
        File f = new File(saveName);
        if(!f.exists() && !f.isDirectory()) {
            Scanner reader = new Scanner(System.in);
            System.out.println(ASK_PREFIX);
            String name = reader.next();
            csvWriter.writeSaveName(name);
        }
    }

    /**
     * Read CSV file
     * @param file
     * @return
     * @throws Exception
     */
    public String readFile(String file) throws Exception {

        // Get working path
        String fileNamePath = Common.getWorkingPath(file);

        // ArrayList for output
        ArrayList<String> output = new ArrayList<>();

        // Read CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(fileNamePath))) {
            String line = "";
            while((line = br.readLine()) != null) {

                // Add it to output
                output.add(line);
            }
        }

        // Return result
        return String.join(", ", output);
    }

    // Method for saving codes
    private void save(String task) throws Exception {
        switch (task) {
            case AFAS_CODES:
                csvWriter.writeCodes(afasCodes.toString(), task);
                break;
            case EMPLOYEE_CODES:
                csvWriter.writeCodes(employeeCodes.toString(), task);
                break;
            default:
                break;
        }
    }

    /**
     * Get mapping from local hashmaps
     * @param key
     * @param situation
     * @return
     * @throws Exception
     */
    public String getMapping(String key, String situation) throws Exception{

        String value = "";

        switch (situation) {
            case AFAS_CODES:
                connectCode(key, AFAS_CODES);
                value = afasCodes.get(key);
                break;
            case EMPLOYEE_CODES:
                connectCode(key, EMPLOYEE_CODES);
                value = employeeCodes.get(key);
                break;
            default:
                break;
        }
        return value.toString();
    }
}
