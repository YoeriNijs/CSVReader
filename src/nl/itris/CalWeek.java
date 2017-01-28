package nl.itris;

import java.util.*;

/**
 * This class can be used for returning the current week number
 * @author Yoeri Nijs
 */
public class CalWeek {

    // Get current week
    public static Integer getWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
}
