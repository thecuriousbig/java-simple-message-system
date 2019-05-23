/**
 * Authentication
 *
 * The Authentication class help the client do the operation <br>
 * about verify or validate the identity of client such as login <br>
 * or register or logout.
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (Mai) 59070501060 <br>
 * On 20-May-2019
 */
public class Authentication
{

    /**
     * This method perform the login operation by get the credential of user
     * such as username and password as the parameter.
     * Then this method will send login request to the server to verify the user.
     *
     * @param username username of the user that want to login.
     * @param password password of the user.
     * @return the flag that represent the overall status of this operation.
     */
    public static boolean login(String username, String password)
    {
        /* Create the packet that will send to the server */
        Packet authPacket = new Packet().setCommand("login").setUsername(username).setPassword(password);

        /* Get the ConnectionManager instance */
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        /* Call the auth operation in connectionManager */
        return connectionManager.authOperation(authPacket, "Login");
    }

    /**
     * This method perform the register operation by get the credential of user
     * such username password as the parameter.
     * <br>
     * With the help of ConnectionManager, Allows this method to connect to
     * the server to perform the register operation properly.
     *
     * @param username username of the user
     * @param password password of the user
     * @return The flag that represents the overall status of this operation.
     */
    public static boolean register(String username, String password)
    {
        /* Create the authPacket that will send to the server */
        Packet authPacket = new Packet().setCommand("register").setUsername(username).setPassword(password);

        /* Calling connectionManager getInstance() to get the instance */
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        /* Calling the authOperation to perform the register request */
        return connectionManager.authOperation(authPacket, "register");
    }

    /**
     * Logout from the system by set the client to null
     *
     * @param client Client that are currently login to the system.
     * @return the boolean that represent the successful of loggin out operation.
     */
    public static boolean logout(Client client)
    {
        /* Make the client =  null */
        client = null;

        return true;
    }
}
