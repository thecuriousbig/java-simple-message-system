/**
 * ClientSystem
 *
 * The ClientSystem class that represent the user interface <br>
 * use for asking the user input and perform some method such as <br>
 * show the main menu.
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */
public class ClientSystem
{
    /** The flag that check if the user is currently logged in or not */
    private static boolean isUserLogin = false;

    /** The client instance that represent the user itself which will initialize after user is logged in */
    private static Client client = null;

    /**
     * Method that show the menu or user interface of this system.
     *
     * @return The flag that check if the user want to exit the program or not.
     */
    public static boolean showMenu()
    {
        /* Declare and initialize the variable that use in this menu */
        String userInput;
        String username;
        String password;
        String confirmPassword;
        boolean isExit = false;
        boolean registerFlag;

        /* If user is logged in already then show the main menu */
        if (isUserLogin)
        {
            /* Get the user input */
            System.out.println("-------------------------------------------------");
            System.out.println("                     Main Menu                   ");
            System.out.println("-------------------------------------------------");
            System.out.println("1. Create Message");
            System.out.println("2. View the MessageCollection (Inbox or Outbox)");
            System.out.println("3. Log out");
            userInput = IOUtils.getString("Enter answer > ");
            /* User choose one operation do perform */
            switch (userInput)
            {
                /* create the message */
                case "1":
                    client.createMessage();
                    break;

                /* view the messageCollection */
                case "2":
                    boolean notValidAnswer;
                    String choose;
                    /* Ask the user if user want to view inbox or outbox */
                    do
                    {
                        notValidAnswer = false;
                        /* Get the user input */
                        System.out.println("Choose the collection to view");
                        System.out.println("1. Inbox");
                        System.out.println("2. Outbox");
                        choose = IOUtils.getString("Enter answer > ");

                        /* User want to view inbox */
                        if (choose.compareTo("1") == 0)
                            client.showMessageCollection("inbox");

                        /* User want to view outbox */
                        else if (choose.compareTo("2") == 0)
                            client.showMessageCollection("outbox");

                        /* User enter invalid input */
                        else
                        {
                            System.out.println("Invalid Answer. Please enter again.");
                            notValidAnswer = true;
                        }
                    }
                    while (notValidAnswer);
                    break;

                /* User want to log out the program */
                case "3":
                    /* Perform log out */
                    isUserLogin = !(Authentication.logout(client));
                    System.out.println("Logging out ..");

                    /* Thread sleep to make program look more realistic */
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                    }
                    break;
            }

        }
        /* If user are not login to the system */
        else
        {
            /* Get the user input */
            System.out.println("--------------------------------------------------");
            System.out.println("           BMTeam Simple Message System           ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            userInput = IOUtils.getString("Enter answer > ");

            /* User choose one operation to do */
            switch (userInput)
            {
                /* Login */
                case "1":
                    System.out.println("------------------  Login  ------------------");
                    username = null;

                    do
                    {
                        username = IOUtils.getString("Enter username (No special character) : ");
                    }
                    while (IOUtils.getSpecialCharacterCount(username) > 0);

                    password = IOUtils.getString("Enter password : ");

                    /* Perform login */
                    isUserLogin = Authentication.login(username, password);

                    /* If login success */
                    if (isUserLogin)
                    {
                        System.out.println("Entering the program ..");

                        /* Create the new Client for the user */
                        client = new Client(username, password);
                    }
                    else
                        System.out.println("Login failed! Please try again");

                    /* Thread sleep to make program looks more realistic */
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                    }
                    break;

                /* Register */
                case "2":
                    System.out.println("---------------  Register  ----------------");
                    username = null;

                    do
                    {
                        username = IOUtils.getString("Enter username (No special character) : ");
                    }
                    while (IOUtils.getSpecialCharacterCount(username) > 0);

                    password = IOUtils.getString("Enter password :");
                    confirmPassword = IOUtils.getString("Confirm password : ");

                    /* Check if password and confirmPassword is equal */
                    while (!confirmPassword.equals(password))
                    {
                        System.out.println("Error password is not correct");
                        confirmPassword = IOUtils.getString("Confirm password : ");
                    }

                    /* Perform register operation */
                    registerFlag = Authentication.register(username, password);

                    /* If register success */
                    if (registerFlag)
                    {
                        System.out.println("Please login to enter the program ..");
                    }
                    else
                        System.out.println("Register failed! Please try again.");

                    /* Thread sleep to make program look more realistic */
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    break;

                /* Exit the program */
                case "3":

                    isExit = true;
                    /* Thread sleep to make program look more realistic */
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    break;

                /* If user enter the invalid input */
                default:
                    System.out.println("Wrong input. Please try again.");
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    break;
            }
        }

        return isExit;
    }

   /**
    * Main method that are the started point of this system.

    * @param args arguments that pass to this program.
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
