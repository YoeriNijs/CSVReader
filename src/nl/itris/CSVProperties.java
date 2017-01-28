package nl.itris;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class offers standard translations for the most common descriptions
 * @author Yoeri Nijs
 */
public class CSVProperties {

    private static final String PROP_FILENAME = "default.properties";
    private static Properties prop;

    public void setDefaultDefinitions() throws Exception {

        prop = new Properties();
        OutputStream output;
        output = new FileOutputStream(PROP_FILENAME);

        // Definitions for Ontwikkeling
        prop.setProperty("Development", "Ontwikkeling");
        prop.setProperty("Maintenance", "Ontwikkeling");
        prop.setProperty("Puzzler", "Ontwikkeling");
        prop.setProperty("Bugs", "Ontwikkeling");
        prop.setProperty("Bug", "Ontwikkeling");
        prop.setProperty("Fixing", "Ontwikkeling");
        prop.setProperty("Fix", "Ontwikkeling");

        // Definitions for Testen
        prop.setProperty("Testing", "Testen");
        prop.setProperty("Tests", "Testen");
        prop.setProperty("Test", "Testen");

        // Definitions for Architectuur
        prop.setProperty("Architectural", "Architectuur");
        prop.setProperty("Architecture", "Architectuur");

        // Definitions for Overleg
        prop.setProperty("Meetings", "Overleg");
        prop.setProperty("Meeting", "Overleg");
        prop.setProperty("Standups", "Overleg");
        prop.setProperty("Standup", "Overleg");
        prop.setProperty("Support", "Overleg");
        prop.setProperty("Supporting", "Overleg");

        // Close file if needed
        if (output != null) {
            output.close();
        }
    }

    public void load() throws Exception {
        InputStream input;
        input = new FileInputStream(PROP_FILENAME);
        prop.load(input);
    }

    public String getDefaultDefinitions(String s) throws Exception {
        String translation = "";
        for (String word : s.split(" ")) {
            if (prop.getProperty(word) != null) {
                translation = prop.getProperty(word);
            } else {
                translation = "Not known";
            }
        }
        return translation;
    }
}
