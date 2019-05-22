/**
 * ClientSystem.java
 *
 * This class is directly contact with user Use for run a main program menu
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */
public class ClientSystem
{
    /* Flag check that user is currently logged in or not */
    private static boolean isUserLogin = false;
    /* client object will initialize after user is logged in */
    private static Client client = null;

    /**
     * showMenu
     *
     * show menu of main program
     */
    public static boolean showMenu()
    {
        String userInput;
        String username;
        String password;
        String confirmPassword;
        boolean isExit = false;
        boolean registerFlag;

        if (isUserLogin)
        {
            System.out.println("-------------------------------------------------");
            System.out.println("                     Main Menu                   ");
            System.out.println("-------------------------------------------------");
            System.out.println("1. Create Message");
            System.out.println("2. Show Inbox");
            System.out.println("3. Log out");
            userInput = IOUtils.getString("Enter answer > ");
            switch (userInput)
            {
                case "1":
                    client.createMessage();
                    break;
                case "2":
                    boolean notValidAnswer;
                    String choose;
                    do
                    {
                        notValidAnswer = false;
                        System.out.println("Choose one collection to view");
                        System.out.println("1. inbox");
                        System.out.println("2. outbox");
                        choose = IOUtils.getString("Enter answer > ");
                        if (choose.compareTo("1") == 0)
                            client.showMessageCollection("inbox");
                        else if (choose.compareTo("2") == 0)
                            client.showMessageCollection("outbox");
                        else
                        {
                            System.out.println("Invalid Answer. Please enter again.");
                            notValidAnswer = true;
                        }
                    }
                    while (notValidAnswer);
                    break;
                case "3":
                    isUserLogin = !(Authentication.logout(client));
                    System.out.println("Logging out ..");
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
        else
        {
            System.out.println("--------------------------------------------------");
            System.out.println("           BMTeam Simple Message System           ");
            System.out.println("--------------------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            userInput = IOUtils.getString("Enter answer > ");
            switch (userInput) {

            case "1":
                System.out.println("------------------  Login  ------------------");
                username = null;
                do
                {
                    /* user enter username */
                    username = IOUtils.getString("Enter username (No special character) : ");
                }
                while (IOUtils.getSpecialCharacterCount(username) > 0);
                /* user enter password */
                password = IOUtils.getString("Enter password : ");

                isUserLogin = Authentication.login(username, password);
                if (isUserLogin)
                {
                    System.out.println("Login success !!");
                    System.out.println("Entering the program ..");
                    client = new Client(username, password);

                }
                else
                    System.out.println("Login failed! Please try again");
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;

            case "2":
                System.out.println("---------------  Register  ----------------");
                // System.out.print("Enter username (No special character) : ");
                username = null;
                do
                {
                    /* user enter username */
                    username = IOUtils.getString("Enter username (No special character) : ");
                }
                while (IOUtils.getSpecialCharacterCount(username) > 0);
                /* user enter password */
                password = IOUtils.getString("Enter password :");
                /* user enter confirm password */
                confirmPassword = IOUtils.getString("Confirm password : ");

                while (!confirmPassword.equals(password))
                {
                    System.out.println("Error password is not correct");
                    confirmPassword = IOUtils.getString("Confirm password : ");
                }

                /* Call the register method */
                registerFlag = Authentication.register(username, password);
                if (registerFlag)
                {
                    System.out.println("Register success !!");
                    System.out.println("Please login to enter the program ..");
                }
                else
                    System.out.println("Register failed! Please try again.");
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;

            case "3":
                isExit = true;
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                break;

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
     * main fucntion
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
