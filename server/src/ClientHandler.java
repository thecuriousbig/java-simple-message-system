
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handle the client that connect to the server.
 * Receive a connection from client and analyze the packet that sent along with the connection.
 * Some operations such as 1. login
 *                         2. register
 *                         3. send the message
 *                         4. get the messages
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035
 *            Manchuporn Pungtippimanchai (mai) 59070501060
 */

public class ClientHandler implements Runnable
{
    protected Socket clientSocket = null;

    /**
     * Constructor of this class
     * Use for initialize member data
     */
    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    /**
     * run method is the entry point when ThreadPooledServer start a new thread
     */
    @Override
    public void run()
    {
        try
        {
            ArrayList<Message> inboxMessage = null;
            /* Get the input stream from the connected socket */
            InputStream inputStream = clientSocket.getInputStream();
            /* Create a DataInputStream so we can read data from it */
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            /* Create the output stream */
            OutputStream outputStream = clientSocket.getOutputStream();
            /* Create a DataOutputStream so we can write data to it */
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            /* Read input object from the socket */
            Packet inputPacket = (Packet) objectInputStream.readObject();
            System.out.println(inputPacket.toString());
            boolean isSuccessFlag = true;
            /* Analyze the packet and handle their actions */
            switch (inputPacket.getCommand())
            {
                case "login":
                    isSuccessFlag = AuthHandler.login(inputPacket.getUsername(), inputPacket.getPassword());
                    if (isSuccessFlag)
                        System.out.println("Login successfully ..");
                    else
                        System.out.println("Login failed ..");
                    break;
                case "register":
                    isSuccessFlag = AuthHandler.register(inputPacket.getUsername(), inputPacket.getPassword());
                    if (isSuccessFlag)
                        System.out.println("Register successfully ..");
                    else
                        System.out.println("Register failed ..");
                    break;
                case "sendMessage":
                    isSuccessFlag = MessageHandler.send(inputPacket.getMessage(), inputPacket.getUsername());
                    if (isSuccessFlag)
                        System.out.println("Send message successfully ..");
                    else
                        System.out.println("Send message failed ..");
                    break;
                case "replyMessage":
                    isSuccessFlag = MessageHandler.reply(inputPacket.getMessage(), inputPacket.getUsername());
                    if (isSuccessFlag)
                        System.out.println("Reply message successfully ..");
                    else
                        System.out.println("Reply message failed ..");
                    break;
                case "deleteMessage":
                    isSuccessFlag = MessageHandler.delete(inputPacket.getMessage(), inputPacket.getUsername());
                    if (isSuccessFlag)
                        System.out.println("Delete message successfully ..");
                    else
                         System.out.println("Delete message failed ..");
                    break;
                case "getMessage":
                    inboxMessage = MessageHandler.getMessage(inputPacket.getUsername());
                    if (isSuccessFlag)
                        System.out.println("Get messages successfully ..");
                    else
                        System.out.println("Get messages failed ..");
                    break;
                default:
                    isSuccessFlag = false;
                    System.out.println("Invalid request ..");
                    break;
            }

            /* Create output packet */
            /* Other parameter is set to null because client don't actually need it */
            Packet outPacket = new Packet().setInboxMessage(inboxMessage).setIsSuccess(isSuccessFlag);

            /* Check what command it is */
            objectOutputStream.writeObject(outPacket);
            System.out.println("Response the client succesfully ..");
            /* input and output stream close */
            /* wait a second to make sure input and output stream success doing their job before close */
            outputStream.close();
            objectOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
