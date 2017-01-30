package nl.itris;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class offers standard translations and codes for the most common descriptions
 * @author Yoeri Nijs
 */
public class CSVProperties {

    private static final String NOT_KNOWN = ""; // empty due to AFAS import standards
    private static final String PROP_PHASE_FILENAME = "phase.properties";
    private static final String PROP_CODE_FILENAME = "code.properties";
    private static final String DEVELOPMENT = "Ontwikkeling";
    private static final String TESTING = "Testen";
    private static final String ARCHITECTURE = "Architectuur";
    private static final String MEETING = "Overleg";
    private static Properties propPhase;
    private static Properties propCode;

    public void setPhaseDefinitions() throws Exception {

        propPhase = new Properties();
        OutputStream output;
        output = new FileOutputStream(PROP_PHASE_FILENAME);

        // Definitions for Ontwikkeling
        propPhase.setProperty("Development", DEVELOPMENT);
        propPhase.setProperty("Maintenance", DEVELOPMENT);
        propPhase.setProperty("Puzzler", DEVELOPMENT);
        propPhase.setProperty("Bugs", DEVELOPMENT);
        propPhase.setProperty("Bug", DEVELOPMENT);
        propPhase.setProperty("Fixing", DEVELOPMENT);
        propPhase.setProperty("Fix", DEVELOPMENT);

        // Definitions for Testen
        propPhase.setProperty("Testing", TESTING);
        propPhase.setProperty("Tests", TESTING);
        propPhase.setProperty("Test", TESTING);

        // Definitions for Architectuur
        propPhase.setProperty("Architectural", ARCHITECTURE);
        propPhase.setProperty("Architecture", ARCHITECTURE);

        // Definitions for Overleg
        propPhase.setProperty("Meetings", MEETING);
        propPhase.setProperty("Meeting", MEETING);
        propPhase.setProperty("Standups", MEETING);
        propPhase.setProperty("Standup", MEETING);
        propPhase.setProperty("Support", MEETING);
        propPhase.setProperty("Supporting", MEETING);

        // Close file if needed
        if (output != null) {
            output.close();
        }
    }

    public void setCodeDefinitions() throws Exception {

        propCode = new Properties();
        OutputStream output;
        output = new FileOutputStream(PROP_CODE_FILENAME);

        // Code for Ontwikkeling
        propCode.setProperty(DEVELOPMENT, "1");

        // Code for Testen
        propCode.setProperty(TESTING, "2");

        // Code for Architectuur
        propCode.setProperty(ARCHITECTURE, "3");

        // Code for Overleg
        propCode.setProperty(MEETING, "4");

        // Close file if needed
        if (output != null) {
            output.close();
        }
    }

    public void load() throws Exception {
        InputStream inputPhase;
        inputPhase = new FileInputStream(PROP_PHASE_FILENAME);
        propPhase.load(inputPhase);

        InputStream inputCode;
        inputCode = new FileInputStream(PROP_CODE_FILENAME);
        propCode.load(inputCode);
    }

    public String getPhaseDefinitions(String s) throws Exception {
        String translation = "";
        for (String word : s.split(" ")) {
            if (propPhase.getProperty(word) != null) {
                translation = propPhase.getProperty(word);
            } else {
                translation = NOT_KNOWN;
            }
        }
        return translation;
    }

    public String getCodeDefinitions(String s) throws Exception {
        String code = "";
        for (String word : s.split(" ")) {
            if (propCode.getProperty(word) != null) {
                code = propCode.getProperty(word);
            } else {
                code = NOT_KNOWN;
            }
        }
        return code;
    }
}
