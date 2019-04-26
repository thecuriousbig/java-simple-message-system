import java.net.Socket;

/**
 * ServerHandler.java
 * 
 * Handle all operation that relate to connect or communicate with server
 * 
 * Created by Tanatorn Nateesanprasert (big) 59070501035
 *            Manchuporn Pungtippimanchai (mai) 59070501060
 */

public class ServerHandler extends Thread
{
    private Socket conn;

    /**
     * Constructor use to initialize socket object
     * @param conn
     */
    ServerHandler(Socket conn)
    {
        this.conn = conn;
    }

    @Override
    public void run()
    {
        /* Implement here */
    }

    /**
     * Send any object from client to server
     * @param obj object that want to send to server
     * @return success flag of this send operation
     */
    public boolean send(Object obj)
    {
        /* Implement here */
        return true;
    }

    /**
     * Receive any object that sent from server
     * @return object that sent from server
     */
    public Object receive()
    {
        /* Implement here */
    }

    /**
     * Tear down the connection between client and server
     */
    public void close()
    {
        /* Implement here */
    }
}
