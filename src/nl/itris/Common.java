package nl.itris;

import java.io.*;

/**
 * @authors Tinie Sluijter and Yoeri Nijs
 * This class holds all common methods
 */
public class Common {

	private static final String OUTPUT_DIR = "CSVReader";
	private static final String NO_SUPPORT = "No support for this operating system yet. Use Linux or Windows. Discontinue.";

	/**
	 * Method for determining current working path
	 *
	 * @param File Filename
	 * @return Working path
	 */
	public static String getWorkingPath(String File) {

		// Determine OS and user home
		String operatingSystem = System.getProperty("os.name").split(" ")[0];
		String userHome = System.getProperty("user.home");
		String workingPath = "";

		// Set the correct path for storing the output
		switch (operatingSystem) {
			case "Linux":
				workingPath = userHome + "/Documents/" + OUTPUT_DIR + "/";
				break;
			case "Windows":
				workingPath = userHome + "\\Documents\\" + OUTPUT_DIR + "\\";
				break;
			default:
				workingPath = NO_SUPPORT;
				break;
		}

		// Create dir if it does not exists
		java.io.File f = new File(workingPath);
		f.mkdir();

		// Return complete output path
		return workingPath + File;
	}

}
