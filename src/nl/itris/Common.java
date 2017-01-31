package nl.itris;

import java.io.*;
import java.util.Scanner;

/**
 * @authors Tinie Sluijter and Yoeri Nijs
 * This class holds all common methods
 */
public class Common {

	private static final String OUTPUT_DIR = "CSVReader";
	private static final String NO_SUPPORT = "No support for this operating system yet. Use Linux or Windows. Discontinue.";
	private static final String NEW_FILE_NAME = "Enter a new filename (without .csv)";
	private static final String EXIT_PROGRAM = "write 'exit' to cancel and exit this program";

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

	/**
	 * Method to determine whether file exist
	 * @param fileLocation
	 * @return
	 */
	public static Boolean fileExist(String fileLocation) {
		File f = new File(fileLocation);
		if(f.exists() && !f.isDirectory()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Method to create new file name
	 * @return
	 */
	public static String createFileName() {
		System.out.println(NEW_FILE_NAME + " or " + EXIT_PROGRAM + ":");
		Scanner reader = new Scanner(System.in);
		String name = reader.next();

		// Exit program if wanted
		if (name.toLowerCase().equals("exit")) {
			exitProgram();
		}

		// Return filename
		return name;
	}

	/**
	 * Method to exit program
	 */
	public static void exitProgram() {
		System.exit(0);
	}

}
