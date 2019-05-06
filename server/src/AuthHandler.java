import java.io.IOException;

/**
 * AuthHandler.java Handle operation like register login
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */

public class AuthHandler
{

    /**
     * login operation by validate user credential
     *
     * @param client client's credential for validating
     */
    public static boolean login(String username, String password)
    {
        FileManager fileManager = new FileManager();
        boolean isAlreadyExist = false;
        boolean isLoginSuccess;
        /* Check if there is this client's information in database */
        try
        {
            isAlreadyExist = fileManager.open("C:/Users/USER/Desktop/EmailJavaProject/database/user/" + username + ".txt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /* If there is a file */
        if (isAlreadyExist)
        {
            /* Check a password */
            System.out.println("Validating the password ..");
            isLoginSuccess = password.equals(fileManager.getNextLine()) ? true : false;
            if (isLoginSuccess)
                System.out.println("Password matched !!");
            else
                System.out.println("Password not matched !!");
            /* close the file */
            fileManager.close();
        }
        else
            /* login failed because this client are not register yet */
            isLoginSuccess = false;
        return isLoginSuccess;
    }

    /**
     * create a file and store the client's information
     *
     * @param client client's credential for creating a file
     */
    public static boolean register(String username, String password)
    {
        FileManager fileManager = new FileManager();
        boolean bOk = false;
        String path = "C:/Users/USER/Desktop/EmailJavaProject/database/user/" + username + ".txt";

        /* create a file */
        bOk = fileManager.create(path);

        if (!bOk)
            return false;
        try
        {
            /* open */
            bOk = fileManager.open(path);
            if (bOk)
                /*write a password */
                bOk = fileManager.setNextLine(password);
            /* close */
            fileManager.close();
        }
        catch (IOException e)
        {
            bOk = false;
        }

        return bOk;
    }
}
