/**
 * IOUtils
 *
 * The {@code IOUtils} class for perform the utilities operation such as <br>
 * get the String from user input and get the integer from user input. <br>
 * <p>
 * Modified by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *             Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class IOUtils
{

    /** Scanner for reading the input from terminal */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Asks for a string and returns it as the value of the function
     *
     * @param prompt String to print, telling which coordinate
     * @return The string the user entered (maximum 100 chars long)
     */
    public static String getString(String prompt)
    {
        String inputString = "";
        System.out.print(prompt);
        /* Read the user input */
        inputString = scanner.nextLine();

        return inputString;
    }

    /**
     * Asks for an integer and returns it as the value of the function
     *
     * @param prompt String to print, telling which coordinate
     * @return value entered. If not an integer, prints an error message and returns
     *         -999
     */
    public static int getInteger(String prompt)
    {
        int input = 0;
        System.out.print(prompt);
        /* Read the user input */
        input = scanner.nextInt();

        return input;
    }

    /**
     * Reads a string and returns it as the value of the function, without any
     * prompt. Remove the newline before returning.
     *
     * @return The string the user entered (maximum 100 chars long)
     */
    public static String getBareString()
    {
        String inputString = "";
        /* Read the user input */
        inputString = scanner.nextLine();

        return inputString;
    }

    /**
     * Change the delimeter of the scanner
     *
     * @param pattern The pattern that will be delimeter of the scanner
     */
    public static void changeDelimeter(String pattern)
    {
        scanner.useDelimiter(pattern);
    }

    /**
     * Creates and returns a string with the current date and time, to use as a time
     * stamp.
     *
     * @return date/time string in the form "yyyy-mm-dd hh:mm:ss"
     */
    public static String getDateTime()
    {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(now);
    }

    /**
     * Reads a string and returns it as the value of the function, without any
     * prompt.
     *
     * @param s String to check special character.
     * @return The number of special character in a string.
     */
    public static int getSpecialCharacterCount(String s)
    {
        /* Trim the whitespace and check if the String is empty or not */
        if (s == null || s.trim().isEmpty())
        {
            System.out.println("Incorrect format of string");
            return 0;
        }

        /* Create the pattern for validating the String */
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        boolean b = m.find();

        /* If pattern found on the String then return the groupCount */
        if (b == true)
        {
            System.out.println("Invalid username. Certain special characters are not allowed in username");
            return m.groupCount();
        }
        else
            return 0;
    }
}
