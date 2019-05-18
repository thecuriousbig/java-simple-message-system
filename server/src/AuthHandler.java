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
        String namePath = "C:/Users/USER/Desktop/EmailJavaProject/database/user/" + username + ".txt";
        isAlreadyExist = fileManager.openRead(namePath);
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
            fileManager.closeRead();
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

        fileManager.createDir("../../database/user/");
        fileManager.createDir("../../database/inbox/");

        String namePath = "../../database/user/" + username + ".txt";

        /* create a file */
        bOk = fileManager.create(namePath);

        if (!bOk)
            return false;
        /* open */
        bOk = fileManager.openWrite(namePath);
        /* write a password */
        if (bOk)
            bOk = fileManager.writeNextLine(password);
        /* close */
        fileManager.closeWrite();

        /* Create message path for this client */
        bOk = fileManager.createDir("../../database/inbox/"+username);

        return bOk;
    }
}
