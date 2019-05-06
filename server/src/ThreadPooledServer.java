import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadPooledServer.java
 *
 * This class is based on the multithreaded server.
 * The main difference is the server loop. Rather than starting
 * a new thread per incoming connection, the connection is wrapped in a
 * Runnable and handed off to a thread pool with the fixed number of thread.
 *
 * Created By Tanatorn Nateesanprasert (big) 59070501035
 *            Manchuporn Pungtippimanchai (mai) 59070501060
 */

public class ThreadPooledServer implements Runnable
{
    /* port of server is 8080 */
    protected int serverPort = 8080;
    protected ServerSocket serverSocket = null;
    /* flag for checking if thread is stopped */
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    /* maximum number of thread that this server can handle at a time */
    protected ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * Constructor of this class
     * Use for defining the port of this server
     */
    public ThreadPooledServer(int port)
    {
        this.serverPort = port;
    }

    @Override
    public void run()
    {
        System.out.println("start Thread Server!!");
        synchronized(this)
        {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped())
        {
            Socket clientSocket = null;
            try
            {
                clientSocket = this.serverSocket.accept();
                System.out.println("client connect : " + clientSocket.toString());
            }
            catch (IOException e)
            {
                if (isStopped())
                {
                    System.out.println("Server Stopped");
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }

            this.threadPool.execute(new ClientHandler(clientSocket));
        }

        this.threadPool.shutdown();
        System.out.println("Server Stopped");
    }

    private synchronized boolean isStopped()
    {
        return this.isStopped;
    }

    public synchronized void stop()
    {
        this.isStopped = true;
        try
        {
            this.serverSocket.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket()
    {
        try
        {
            this.serverSocket = new ServerSocket(this.serverPort);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    /**
     * Main method for running the server
     * @param argv argument that pass by the user
     */
    public static void main(String argv[])
    {
        /* create instance of ThreadPooledServer */
        ThreadPooledServer server = new ThreadPooledServer(8080);
        /* Create new thread of server and run the server */
        new Thread(server).start();
        // try
        // {
        //     Thread.sleep(20 * 1000);
        // }
        // catch (InterruptedException e)
        // {
        //     e.printStackTrace();
        // }
        // System.out.println("Stopping Server");
        // server.stop();
    }


}
