/**
 * ClientSystem.java
 *
 * This class is directly contact with user Use for run a main program menu
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */
public class ClientSystem {
    /* Flag check that user is currently logged in or not */
    private static boolean isUserLogin = false;
    /* client object will initialize after user is logged in */
    private static Client client = null;

    /**
     * Constructor of ClientSystem
     */
    ClientSystem() {

    }

    /**
     * showMenu
     *
     * show menu of main program
     */
    public static boolean showMenu() {
        String userInput;
        boolean isExit = false;
        boolean registerFlag;

        if (isUserLogin) {

            System.out.println("                    Main Menu                   ");
            System.out.println("================================================");
            System.out.println("1. Create Message");
            System.out.println("2. Show Inbox");
            System.out.println("3. Log out");
            userInput = IOUtils.getString("Enter answer > ");
            switch (userInput)
            {
                case "1":
                    client.createMessage();

                case "2":
                    client.showInbox();

                case "3":
                    isUserLogin = !(Authentication.logout(client));
            }

        } else {

            System.out.println("          BMTeam Simple Message System          ");
            System.out.println("================================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            userInput = IOUtils.getString("Enter answer > ");
            switch (userInput) {

            case "1":
                isUserLogin = Authentication.login();
                if (isUserLogin)
                {
                    System.out.println("Login success !!");
                    System.out.println("Entering the program ..");
                }
                else
                    System.out.println("Login failed! Please try again");
                try
                {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;

            case "2":
                registerFlag = Authentication.register();
                if (registerFlag)
                {
                    System.out.println("Register success !!");
                    System.out.println("Please login to enter the program ..");
                }
                else
                    System.out.println("Register failed! Please try again.");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;

            case "3":
                isExit = true;
                break;

            default:
                System.out.println("Wrong input. Please try again.");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
        }

        return isExit;
    }

    /**
     * main fucntion
     *
     * Everything begins here
     *
     * @param args[] array of input argument
     */
    public static void main(String args[]) {
        boolean isExit;
        do {
            isExit = showMenu();
        } while (!isExit);
        System.out.println("Exit program!");
        System.exit(0);
    }
}
