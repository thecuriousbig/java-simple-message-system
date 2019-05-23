import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.Socket;

/**
 * ServerHandler
 *
 * The ServerHandler class is the class that perform the communication <br>
 * from client to server.
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (mai) 59070501060 <br>
 * On 20-May-2019
 */
public class ServerHandler
{
    /** Address of the server */
    private String address;
    /** Port number of the server */
    private int port;
    /** Socket object that use to connect with the server */
    private Socket socket = null;

    /**
     * Constructor of ServerHandler.<br>
     * Initialize the member data of this class.
     *
     * @param address address of the server
     * @param port port number to connect with the server
     */
    public ServerHandler(String address, int port)
    {
        this.address = address;
        this.port = port;
    }

    /**
     * ServerHandler use socket to establish the connection to the server.
     * By create the socket and passing the address and port number to it.
     *
     * @return the flag that represent the status of connection.
     */
    public boolean connect()
    {
        /* boolean flag that represent the status of this operation */
        boolean connectionStatus = true;
        try
        {
            /* Establish the connection with the server */
            this.socket = new Socket(this.address, this.port);
            System.out.println("Connecting to the server ..");
        }
        catch (UnknownHostException ue)
        {
            connectionStatus = false;
        }
        catch (IOException ie)
        {
            connectionStatus = false;
        }

        return connectionStatus;
    }

    /**
     * Sending any packet from the client to the server.
     * Sending packet as the type of object using ObjectOutputStream.
     *
     * @param packet packet that want to send to server.
     * @return the flag that represent the overall status of this operation.
     */
    public boolean send(Packet packet)
    {
        boolean sendPacketStatus = true;
        try
        {
            /* Create ObjectOutputStream to send an object to server */
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            /* Send the packet to server */
            objectOutputStream.writeObject(packet);
        }
        catch (Exception e)
        {
            sendPacketStatus = false;
        }

        return sendPacketStatus;
    }

    /**
     * Receive any packet that sent from the server.
     * The packet that are received is the ObjectInputStream type.
     *
     * @return The packet that received from the server.
     */
    public Packet receive()
    {
        /* Declare the variable to receive the packet that sent from the server */
        Packet receivePacket = null;
        try
        {
            /* Create objectInputStream to receive an object from server */
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            /* looping until server send something */
            while (receivePacket == null)
            {
                /* Receive the packet from the server */
                receivePacket = (Packet) objectInputStream.readObject();
            }

            /* Thread sleep about 500ms for waiting the server to actually finish sending before closing */
            Thread.sleep(500);

            /* Closing the ObjectInputStream */
            objectInputStream.close();
            inputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return receivePacket;
    }

    /**
     * ServerHandler perform the closing connection by teardown.
     * and close the socket.
     */
    public void close()
    {
        try
        {
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println("Closing socket FAILED");
        }
    }
}
