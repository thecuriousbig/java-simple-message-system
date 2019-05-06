import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.Socket;
// import java.io.Serializable;

/**
 * ServerHandler.java
 *
 * Handle all operation that relate to connect or communicate with server
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035
 *            Manchuporn Pungtippimanchai (mai) 59070501060
 */
public class ServerHandler
{
    // private static final long serialVersionUID = 1L;
    protected String address;
    protected int port;
    protected Socket socket = null;

    /**
     * Constructor use to initialize socket
     * @param address address of the server
     * @param port port number to connect with the server
     */
    public ServerHandler(String address, int port)
    {
        this.address = address;
        this.port = port;
    }

    public boolean connect()
    {
        boolean isSuccess = true;
        // establish a connection with server
        try
        {
            this.socket = new Socket(this.address, this.port);
            System.out.println("connecting to the server ..");
        }
        catch (UnknownHostException ue)
        {
            System.out.println(ue);
            isSuccess = false;
        }
        catch (IOException i)
        {
            System.out.println(i);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * Send any object from client to server
     * @param obj object that want to send to server
     * @return success flag of this send operation
     */
    public boolean send(Packet packet)
    {
        boolean isSuccess = true;
        try
        {
            /* Create objectOutputStream to send an object to server */
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            /* Send the packet to server */
            objectOutputStream.writeObject(packet);

            /* close the outputstream */
            /* wait a second to make sure that outputstream is finish their job before close it */
            // outputStream.close();
            // objectOutputStream.close();
        }
        catch (Exception e)
        {
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * Receive any object that sent from server
     * @return packet that sent from server
     */
    public Packet receive()
    {
        Packet receivePacket = null;
        try
        {
            /* Create objectInputStream to receive an object from server */
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            /* looping until server send something */
            while (receivePacket == null)
            {
                receivePacket = (Packet) objectInputStream.readObject();
            }
            Thread.sleep(500);
            inputStream.close();
            objectInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return receivePacket;
    }

    /**
     * Tear down the connection between client and server
     */
    public void close()
    {
        try
        {
            socket.close();
        }
        catch (Exception e)
        {
            System.out.println("close socket failed");
        }
    }
}
