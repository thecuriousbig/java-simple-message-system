/**
 * ThreadPooledServer
 *
 * The {@code ThreadPooledServer} class that represent the server <br>
 * that based on the multithreaded server.
 * <p>
 * Created By Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (mai) 59070501060 <br>
 * On 20-May-2019
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable
{
    /** port of server is 8080 */
    private int serverPort = 8080;

    /** Server socket object */
    private ServerSocket serverSocket = null;

    /** flag for checking if thread is stopped */
    private boolean isStopped = false;

    /** Thread that will be folk to handle the client */
    private Thread runningThread = null;

    /** maximum number of thread that this server can handle at a time */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * Constructor of this class.
     * Use for defining the port of this server.
     *
     * @param port port number of this server.
     */
    public ThreadPooledServer(int port)
    {
        this.serverPort = port;
    }

    /**
     * Method that will be called when the server start.
     * This method will waiting for the client to successfully connect
     * to the server. And It will assign one Thread in the ThreadPooled
     * to handle the client for it.
     */
    @Override
    public void run()
    {
        System.out.println("start Thread Server!!");

        /* Synchronized the thread */
        synchronized(this)
        {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();

        /* run the server until server stopped */
        while (!isStopped())
        {
            /* Create the client socket */
            Socket clientSocket = null;
            try
            {
                /* wait until client physically connect and the server accept the connection */
                clientSocket = this.serverSocket.accept();
                System.out.println("client connect : " + clientSocket.toString());
            }
            catch (IOException e)
            {
                /* If server stopped */
                if (isStopped())
                {
                    System.out.println("Server Stopped");
                    break;
                }
                /* Throw the runtimeException */
                throw new RuntimeException("Error accepting client connection", e);
            }

            /* If everything is okay, Assign one thread to handle the client */
            this.threadPool.execute(new ClientHandler(clientSocket));
        }

        /* ThreadPool shutdown when handle the client success to reduce CPU */
        this.threadPool.shutdown();
        System.out.println("Server Stopped");
    }

    /**
     * Method that check if the server is stopped or not.
     *
     * @return the boolean value that represent the status of the server.
     */
    private synchronized boolean isStopped()
    {
        return this.isStopped;
    }

    /**
     * Method to stop the server from running.
     */
    public synchronized void stop()
    {
        /* Set the flag that check if server is stopped or not to TRUE */
        this.isStopped = true;
        try
        {
            /* Close the serverSocket */
            this.serverSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error closing server", e);
        }
    }

    /**
     * Open the server socket to allow client to connect to the server
     */
    private void openServerSocket()
    {
        try
        {
            /* Create the serverSocket */
            this.serverSocket = new ServerSocket(this.serverPort);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    /**
     * Main method for running the server
     *
     * @param argv argument that pass by the user
     */
    public static void main(String argv[])
    {
        /* create instance of ThreadPooledServer */
        ThreadPooledServer server = new ThreadPooledServer(8080);
        /* Create new thread of server and run the server */
        new Thread(server).start();
    }


}
