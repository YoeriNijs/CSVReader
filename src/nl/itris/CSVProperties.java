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
        propPhase.setProperty("development", DEVELOPMENT);
        propPhase.setProperty("maintenance", DEVELOPMENT);
        propPhase.setProperty("puzzler", DEVELOPMENT);
        propPhase.setProperty("bugs", DEVELOPMENT);
        propPhase.setProperty("bug", DEVELOPMENT);
        propPhase.setProperty("fixing", DEVELOPMENT);
        propPhase.setProperty("fix", DEVELOPMENT);

        // Definitions for Testen
        propPhase.setProperty("testing", TESTING);
        propPhase.setProperty("tests", TESTING);
        propPhase.setProperty("test", TESTING);

        // Definitions for Architectuur
        propPhase.setProperty("architectural", ARCHITECTURE);
        propPhase.setProperty("architecture", ARCHITECTURE);

        // Definitions for Overleg
        propPhase.setProperty("meetings", MEETING);
        propPhase.setProperty("meeting", MEETING);
        propPhase.setProperty("standups", MEETING);
        propPhase.setProperty("standup", MEETING);
        propPhase.setProperty("support", MEETING);
        propPhase.setProperty("supporting", MEETING);

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
        propCode.setProperty(DEVELOPMENT, "55.1");

        // Code for Testen
        propCode.setProperty(TESTING, "52.1");

        // Code for Architectuur
        propCode.setProperty(ARCHITECTURE, "91");

        // Code for Overleg
        propCode.setProperty(MEETING, "");

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
            if (propPhase.getProperty(word.toLowerCase()) != null) {
                translation = propPhase.getProperty(word.toLowerCase());
                break;
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
                break;
            } else {
                code = NOT_KNOWN;
            }
        }
        return code;
    }
}
