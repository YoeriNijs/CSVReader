package nl.itris;

import java.io.*;

/**
 * This class writes output to a csv file
 * @author Yoeri Nijs
 */
public class CSVWriter {

    private static final String SAVE_NAME_FILE = "Name.txt";
    private static final Integer WEEK_NUMBER = CalWeek.getWeek();
    private static final String CSV_OUTPUTFILE = "_week_" + WEEK_NUMBER.toString() + ".csv";
    private static final String AFAS_CODES_FILE = "AfasCodes.txt";
    private static final String EMPLOYEE_CODE = "EmployeeCodes.txt";
    private static final String SEPERATOR = ",";
    private static final String CSV_HEADER = "Jaar,Week,Datum,Aantal uren,Project,Projectomschrijving,Debiteur,Med.code,Medewerker,Reiskosten,Doorbelast,Geaccodeerd,"
        + "Gereed,Gefactureerd,Projectfase,Fase,Code,Melding-nr,Melding door,Memo,Billable\n";
    private CSVProperties setProperties;
    private int nr = 0;
    private static final String AFAS_CODES = "afasCodes";
    private static final String EMPLOYEE_CODES = "employeeCodes";
    private static final String NOT_KNOWN = ""; // Must be empty due to AFAS import standards
    private static final String INPUT_PROVIDER = "Provider";
    private static final String NO = "N";
    private static final String YES = "Y";
    private static final String DEBTOR = "Debtor";
    private static final String BILLABLE = "BILLABLE";

    /**
     * Instantiate new CSV writer
     * @throws Exception
     */
    public CSVWriter() throws Exception {
        setProperties = new CSVProperties();
        setProperties.load();
    }

    /**
     * Get file that holds the stored prefix
     * @return
     */
    public String getSaveNameFile() {
        return SAVE_NAME_FILE;
    }

    /**
     * Method for writing header to output file
     * @throws Exception
     */
    public void WriteHeader() throws Exception {
        CSVReader r = new CSVReader();
        String customCsvOutputFile = r.readFile(SAVE_NAME_FILE) + CSV_OUTPUTFILE;
        String outputLocation = Common.getWorkingPath(customCsvOutputFile);
        FileWriter writer = new FileWriter(outputLocation);
        writer.write(CSV_HEADER);
        writer.close();
    }

    /**
     * Method for writing row to output file
     * @param output
     * @throws Exception
     */
    public void writeRow(String[] output) throws Exception {
        CSVReader r = new CSVReader();
        StringBuffer out = new StringBuffer();

        // Get year
        String date = output[10];
        String[] dateParts = date.split("-");
        if (dateParts.length < 3) {
            dateParts = date.split("/");
        }
        out = out.append(dateParts[2]).append(SEPERATOR);

        // Get week
        String weekString = new String(output[1]);
        String[] weekParts = weekString.split(" ");
        out = out.append(weekParts[1]).append(SEPERATOR);

        // Get date
        out = out.append(date.replaceAll("/", "-")).append(SEPERATOR);

        // Get number of hours
        out = out.append(output[11]).append(SEPERATOR);

        // Get project
        out = out.append(output[4]).append(SEPERATOR);

        // Get iteration and get the correct mapping
        String iteration = output[5];
        out = out.append(r.getMapping(iteration, AFAS_CODES)).append(SEPERATOR);

        // Get debtor
        out = out.append(DEBTOR).append(SEPERATOR);

        // Get employee and get the correct employee number
        String employee = output[9];
        out = out.append(r.getMapping(employee, EMPLOYEE_CODES)).append(SEPERATOR);
        out = out.append(employee).append(SEPERATOR);

        // Some static variables
        out = out.append(NO).append(SEPERATOR).append(NO).append(SEPERATOR);
        out = out.append(YES).append(SEPERATOR).append(YES).append(SEPERATOR);
        out = out.append(NO).append(SEPERATOR);

        // Get project phase
        out = out.append(NOT_KNOWN).append(SEPERATOR);

        // Get phase
        String story = output[6];
        String translation;
        translation = setProperties.getPhaseDefinitions(story);
        out = out.append(translation).append(SEPERATOR);

        // Get code
        out = out.append(setProperties.getCodeDefinitions(translation)).append(SEPERATOR);

        // Get report number
        out = out.append(NOT_KNOWN).append(SEPERATOR);

        // Get reporter
        out = out.append(INPUT_PROVIDER).append(SEPERATOR);

        // Get memo
        if (output[8] != null && output[8].length() > 0) {
            out = out.append(output[8]).append(SEPERATOR);
        } else {
            out = out.append("").append(SEPERATOR);
        }

        // Get billable
        out = out.append(BILLABLE);

        // New row
        out.append("\n");

        // Write everything to output file
        String customCsvOutputFile = r.readFile(SAVE_NAME_FILE) + CSV_OUTPUTFILE;
        String outputLocation = Common.getWorkingPath(customCsvOutputFile);

        // Overwrite file if already written
        FileWriter writer = new FileWriter(outputLocation, true);

        // Logging, not active. Use when needed.
        // System.out.println(out);

        // Close file
        writer.write(out.toString());
        writer.close();
    }

    /**
     * Method for storing codes to text file
     * @param codes
     * @param task
     * @throws Exception
     */
    public void writeCodes(String codes, String task) throws Exception {
        String file = "";
        switch (task) {
            case AFAS_CODES:
                file = AFAS_CODES_FILE;
                break;
            case EMPLOYEE_CODES:
                file = EMPLOYEE_CODE;
                break;
            default:
                break;
        }
        String outputLocation = Common.getWorkingPath(file);
        FileWriter writer = new FileWriter(outputLocation);
        writer.write(codes);
        writer.close();
    }

    /**
     * Method for storing prefix output files
     * @param name
     * @throws Exception
     */
    public void writeSaveName(String name) throws Exception {
        String saveName = Common.getWorkingPath(SAVE_NAME_FILE);
        FileWriter writer = new FileWriter(saveName, false);
        String out = (name);
        writer.write(out);
        writer.close();
    }

}
