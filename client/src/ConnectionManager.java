/**
 * ConnectionManager
 *
 * The ConnectionManager class represents the Manager that handle <br>
 * all the connection from the server to the client or the client to the server.
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (Mai) 59070501060 <br>
 * On 20-May-2019
 */
public class ConnectionManager
{
    /** Instance of ConnectionManager class. Can access this variable by call getInstance() */
    private static ConnectionManager instance = null;
    /** ServerHandler variable for actually establish the connection to the server */
    private static ServerHandler serverHandler = null;

    /**
     * Constructor of ConnectionManager class.
     *
     * Use for initialize the member data's value when created the instance
     */
    private ConnectionManager()
    {
        serverHandler = new ServerHandler("127.0.0.1", 8080);
    }

    /**
     * Get the instance of ConnectionManager class for access the method
     * to connect to the server. Because ConnectionManager is a Singleton Pattern.
     *
     * So the only to use this class is to use via getInstance().
     * Such as login , register , send the message , request the messages , etc.
     *
     * @return The instance of the ConnectionManager.
     */
    public static ConnectionManager getInstance()
    {
        if (instance == null)
            instance = new ConnectionManager();
        return instance;
    }

    /**
     * Open and establish the connection from the client to the server.
     *
     * @return The flag that represent the connection status.
     */
    public boolean establishConnection()
    {
        /* Use the connect method from the ServerHandler */
        boolean connectionStatus = serverHandler.connect();

        if (connectionStatus)
            System.out.println("Establish the connection to the server SUCCESS.");
        else
            System.out.println("Establish the connection to the server FAILED.");

        return connectionStatus;
    }

    /**
     * ConnectionManager perform the authenticate operation like login or register
     * by connect to the server and send the authenticate Packet to the server.
     * Then the server will verify the identity of the client.
     *
     * @param authPacket The packet that will send to the server. Contains all the client's credential such as username and password.
     * @param type Type of operation that will perform in this method.
     * @return the flag that represent the overall status of authenticate operation.
     */
    public boolean authOperation(Packet authPacket, String type)
    {
        try
        {
            /* First connect to the server */
            boolean connectionStatus = establishConnection();
            /* If the connection is not okay then terminate the operation */
            if (!connectionStatus)
            {
                System.out.println("Terminate the " + type + " request.");
                return false;
            }

            /* If the connection is okay then send the packet to the server */
            boolean sendPacketStatus = serverHandler.send(authPacket);
            /* If the problem occurred then terminate the operation */
            if (!sendPacketStatus)
            {
                System.out.println("Send packet to the server FAILED.");
                System.out.println("Terminate the " + type + " request.");
                return false;
            }

            System.out.println("Waiting for response ..");
            /* Thread sleep for waiting the server to working properly */
            try
            {
                Thread.sleep(1000);
            } catch (Exception e)
            {
            }

            /* If everything is okay then wait for server's response */
            Packet serverPacket = serverHandler.receive();

            /* If packet is null. Its mean server work not properly */
            if (serverPacket.equals(null))
            {
                System.out.println("Packet from server corrupted.");
                System.out.println("Terminal the " + type + " request.");
                return false;
            }
            else
            {
                if (serverPacket.getIsSuccess())
                    System.out.println(type + " operation SUCCESS");
                else
                    System.out.println(type + " operation FAILED");
            }

            /* If everything looks good then finish the login or register operation by teardown the connection */
            tearDownConnection();

            return serverPacket.getIsSuccess();
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred in " + type + " operation.");
            return false;
        }
    }

    /**
     * ConnectionManager perform operation about sending of forwarding or replying the message.
     * By sending the packet to the server.
     * So that server will operate and store the message to the database.
     *
     * @param messagePacket packet that contains the message that are going to be sent the server.
     * @param type Type of operation that will perform in this method.
     * @return the flag that represent the overall status of sendind or forwarding of replying the message operation.
     */
    public Packet messageOperation(Packet messagePacket, String type)
    {
        Packet serverPacket = new Packet();
        try
        {
            /* First connect to the server */
            boolean connectionStatus = establishConnection();

            /* If the connection is not okay then terminate the operation */
            if (!connectionStatus)
            {
                System.out.println("Terminate the " + type + " request.");

                /* The serverPacket is currently null */
                return serverPacket.setIsSuccess(false);
            }

            /* If the connection is okay then send the packet to the server */
            boolean sendPacketStatus = serverHandler.send(messagePacket);

            /* If the problem occurred then terminate the operation */
            if (!sendPacketStatus)
            {
                System.out.println("Send packet to the server FAILED.");
                System.out.println("Terminate the " + type + " request.");
                /* The serverPacket is currently null */
                return serverPacket.setIsSuccess(false);
            }

            System.out.println("Waiting for response ..");

            /* Thread sleep for waiting server to working properly */
            Thread.sleep(1000);

            /* reset the serverPacket */
            serverPacket = null;

            /* If everything is okay then wait for server's response */
            serverPacket = serverHandler.receive();

            /* If packet is null. Its mean server work not properly */
            if (serverPacket.equals(null))
            {
                System.out.println("Packet from server corrupted.");
                System.out.println("Terminal the " + type + " request.");
                return serverPacket.setIsSuccess(false);
            }
            else
            {
                /* If send message success */
                if (serverPacket.getIsSuccess())
                    System.out.println(type + " message SUCCESS.");
                else
                    System.out.println(type + " message FAILED");
            }

            /* If everything looks good then finish the register operation by teardown the connection */
            tearDownConnection();

            return serverPacket;
        }
        catch (Exception e)
        {
            System.out.println("Error Occurred in " + type + " operation.");
            return null;
        }
    }

    /**
     * Close and teardown the connection from the client to the server.
     */
    public void tearDownConnection()
    {
        serverHandler.close();
    }
}
