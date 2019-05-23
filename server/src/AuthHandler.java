/**
 * AuthHandler
 *
 * The {@code AuthHandler} class that represent that class that act <br>
 * like the service that get the authentication request from the client <br>
 * And then perform operation (serve the client) like Login or register.
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */

public class AuthHandler
{

    /**
     * Method to perform the login operation by receiving the
     * username and password as the parameter. In order to perform the
     * validating the user's credential that sent from the client.
     * This method operate by calling {@code FileManager} to do the
     * read and write the file for it.
     *
     * @param username the username that client sent to the server.
     * @param password the password that will be stored in the database.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean login(String username, String password)
    {
        FileManager fileManager = new FileManager();

        /* Declare and initialize the variable use for validate user credential */
        boolean isAlreadyExist = false;
        boolean isLoginSuccess;

        /* pathname that fileManager will searched in */
        String namePath = "../../database/user/" + username + ".txt";

        /* Open the file and check if file is already exist */
        isAlreadyExist = fileManager.openRead(namePath);

        /* If the file is already exist */
        if (isAlreadyExist)
        {
            System.out.println("Validating the password ..");

            /* Validate the password if the password is equivalent to the password in the database */
            isLoginSuccess = password.equals(fileManager.getNextLine()) ? true : false;

            /* If validate correct */
            if (isLoginSuccess)
                System.out.println("Password matched !!");
            else
                System.out.println("Password not matched !!");

            /* close the file */
            fileManager.closeRead();
        }
        else
            /* login failed because this client are not register yet */
            isLoginSuccess = false;

        return isLoginSuccess;
    }

    /**
     * Method to perform the register operation by receiving the
     * username and password as the parameter. This method create
     * the account by checking if this username are already exist
     * in the database. This class perform the register operation
     * by the help of {@code FileManager} to handle all the read
     * and write file operation for this method.
     *
     * @param username username of the user that want to create the account.
     * @param password password of the user that will be stored in the database.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean register(String username, String password)
    {
        FileManager fileManager = new FileManager();

        boolean bOk = false;

        /* FileManager create the directory for storing the file */
        fileManager.createDir("../../database/user/");
        fileManager.createDir("../../database/message-collection/");

        /* namepath that fileManager will perform the write file operation */
        String namePath = "../../database/user/" + username + ".txt";

        /* create a file */
        bOk = fileManager.create(namePath);

        /* If create failed */
        if (!bOk)
            return false;

        /* open and write the file */
        bOk = fileManager.openWrite(namePath);

        if (bOk)
            /* write a password */
            bOk = fileManager.writeNextLine(password);

        /* close the file */
        fileManager.closeWrite();

        /* Create message path for this client */
        bOk = fileManager.createDir("../../database/message-collection/"+username+"/inbox");
        bOk = fileManager.createDir("../../database/message-collection/"+username+"/outbox");

        return bOk;
    }
}
