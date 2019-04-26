import javax.lang.model.util.ElementScanner6;

/**
 * ClientSystem.java
 * 
 * This class is directly contact with user
 * Use for run a main program menu
 * 
 * Created by Tanatorn Nateesanprasert (big) 59070501035
 *            Manchuporn Pungtippimanchai (mai) 59070501060
 */
public class ClientSystem
{
    /* Flag check that user is currently logged in or not */
    private static boolean isUserLogin = false;
    /* client object will initialize after user is logged in */
    private static Client client = null;

    /**
     * Constructor of ClientSystem
     */
    ClientSystem()
    {

    }

    /**
     * showMenu
     * 
     * show menu of main program
     */
    public static boolean showMenu()
    {
        int userInput;
        boolean isExit = false;

        if (isUserLogin)
        {
            
        }
        else
        {
            boolean registerFlag;
            System.out.flush();
            System.out.println("          BMTeam Simple Message System          ");
            System.out.println("================================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            userInput = IOUtils.getInteger("Enter answer >");
            switch (userInput)
            {
                case 0:
                    isExit = true;
                    break;

                case 1:
                    isUserLogin = Authentication.login();
                    if (isUserLogin)
                    {

                    }
                    else
                    {

                    }
                    break;

                case 2:
                    registerFlag = Authentication.register();
                    if (registerFlag)
                    {
                        System.out.println("Register success! Please login to enter the program.");
                        try
                        {
                            Thread.sleep(2000);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    else
                    {
                        System.out.println("Register failed! Please try again.");
                        try
                        {
                            Thread.sleep(2000);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }

                    }
                    break;

                default:
                    System.out.println("Wrong input. Please try again.");
                    break;
            }
        }

        return isExit;
    }

    /**
     * main fucntion
     * 
     * Everything begins here
     * @param args[] array of input argument
     */
    public static void main(String args[])
    {
        boolean isExit;
        do
        {
            isExit = showMenu();
        }
        while (!isExit);
        System.out.println("Exit program!");
        System.exit(0);
    }
}
