import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

/**
 * Class that provides static functions for doing terminal input
 *
 * Created by Sally Goldin, 30 April 2014, for CPE113 Use to simplify Java lab
 * exercises.
 */
public class IOUtils {

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
        input = scanner.nextInt();
        return input;
    }

    /**
     * Reads a string and returns it as the value of the function, without any
     * prompt. Remove the newline before returning.
     *
     * @param prompt String to print, telling which coordinate
     * @return The string the user entered (maximum 100 chars long)
     */
    public static String getBareString()
    {
        String inputString = "";
        inputString = scanner.nextLine();
        return inputString;
    }

    public static void changeDelimeter(String pattern)
    {
        scanner.useDelimiter(pattern);
        System.out.println(scanner.delimiter());
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
        if (s == null || s.trim().isEmpty())
        {
            System.out.println("Incorrect format of string");
            return 0;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == true)
        {
            System.out.println("Invalid username. Certain special characters are not allowed in username");
            return m.groupCount();
        }
        else
            return 0;
    }
}


// import java.io.IOException;
// import java.text.SimpleDateFormat;
// import java.util.Date;

// /**
//  * Class that provides static functions for doing terminal input
//  *
//  *  Created by Sally Goldin, 30 April 2014, for CPE113
//  *  Use to simplify Java lab exercises.
//  */
// public class IOUtils
// {


    //     /**
    //      * Asks for an integer and returns it as the value of the function
    //      * @param   prompt    String to print, telling which coordinate
    //      * @return value entered. If not an integer, prints an error message
    //      * and returns -999
    //      */
    //     public static int getInteger(String prompt)
    //     {
        //        int value = -999;
        //        String inputString;
        //        int readBytes = 0;
        //        byte buffer[] = new byte[200];
        //        System.out.println(prompt);
        //        try
        //        {
            //            readBytes = System.in.read(buffer,0,200);
            //        }
            //        catch (IOException ioe)
            //        {
// 	   System.out.println("Input/output exception - Exiting");
// 	   System.exit(1);
//        }
//        inputString = new String(buffer);
//        try
//        {
    // 	   int pos = inputString.indexOf("\n");
//            if (pos > 0)
// 	      inputString = inputString.substring(0,pos);
//            value = Integer.parseInt(inputString);
//        }
//        catch (NumberFormatException nfe)
//        {
    // 	   System.out.println("Bad number entered");
    //        }
    //        return value;
    //     }

    // /**
    //  * Asks for a string and returns it as the value of the function
    //  * @param   prompt    String to print, telling which coordinate
    //  * @return  The string the user entered (maximum 100 chars long)
    //  */
    // public static String getString(String prompt)
    // {
    //    String inputString;
    //    int readBytes = 0;
    //    byte buffer[] = new byte[200];
    //    System.out.println(prompt);
    //    try
    //    {
    //        readBytes = System.in.read(buffer,0,200);
    //    }
    //    catch (IOException ioe)
    //    {
	//    System.out.println("Input/output exception - Exiting");
	//    System.exit(1);
    //    }
    //    inputString = new String(buffer);
    //    int pos = inputString.indexOf("\n");
    //    if (pos > 0)
	//    inputString = inputString.substring(0,pos);
    //    return inputString;
    // }

    // /**
    //  * Reads a string and returns it as the value of the function,
    //  * without any prompt. Remove the newline before returning.
    //  * @param   prompt    String to print, telling which coordinate
    //  * @return  The string the user entered (maximum 100 chars long)
    //  */
    // public static String getBareString()
    // {
    //    String inputString;
    //    int readBytes = 0;
    //    byte buffer[] = new byte[200];
    //    try
    //    {
    //        readBytes = System.in.read(buffer,0,200);
    //    }
    //    catch (IOException ioe)
    //    {
	//    System.out.println("Input/output exception - Exiting");
	//    System.exit(1);
    //    }
    //    inputString = new String(buffer);
    //    int pos = inputString.indexOf("\n");
    //    if (pos > 0)
	//    inputString = inputString.substring(0,pos);
    //    return inputString;
    // }


//     /**
//      *  Creates and returns a string with the current date
//      *  and time, to use as a time stamp.
//      * @return date/time string in the form "yyyy-mm-dd hh:mm:ss"
//      */
//     public static String getDateTime()
//     {
// 	Date now = new Date();
// 	SimpleDateFormat formatter =
//               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// 	return formatter.format(now);
//     }
// }
