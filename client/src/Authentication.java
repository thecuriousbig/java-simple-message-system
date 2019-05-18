/**
 * Authentication.java
 *
 * handle operations such as register, login and logout
 *
 * Created by Tanatorn Nateesanprasert (Big) 59070501035
 *            Manchuporn Pungtippimanchai (Mai) 59070501060
 */
public class Authentication
{

    public static boolean login(String username, String password)
    {
        //Clear screen
        // System.out.print("\033[H\033[2J");
        // System.out.flush();
        ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
        Packet packet = new Packet().setCommand("login").setUsername(username).setPassword(password);

        /* connect to server */
        boolean isConnect = serverHandler.connect();
        if (!isConnect)
        {
            System.out.println("cannot connect to server!");
            return false;
        }

        /* send request to server */
        boolean sendSuccess = serverHandler.send(packet);
        if (!sendSuccess)
        {
            System.out.println("Request to login failed");
            return false;
        }

        /* wait for server response back */
        Packet receivePacket = serverHandler.receive();
        serverHandler.close();
        return receivePacket.getIsSuccess();
    }

    public static boolean register(String username, String password)
    {
        // System.out.print("\033[H\033[2J");
        //Clear screen
        // System.out.flush();
        ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
        Packet packet = new Packet().setCommand("register").setUsername(username).setPassword(password);

        /* connect to server */
        boolean isConnect = serverHandler.connect();
        if (!isConnect) {
            System.out.println("cannot connect to server!");
            return false;
        }

        /* send request to server */
        boolean sendSuccess = serverHandler.send(packet);
        if (!sendSuccess) {
            System.out.println("Request to register failed");
            return false;
        }

        /* wait for server response back */
        Packet receivePacket = serverHandler.receive();
        serverHandler.close();
        return receivePacket.getIsSuccess();
    }

    public static boolean logout(Client client)
    {
        client = null;
        return true;
    }
}
