import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ClientHandler
 *
 * The ClientHandler class are the class that represent the class that <br>
 * handle the client that are connected to the server.
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (mai) 59070501060 <br>
 * On 20-May-2019
 */
public class ClientHandler implements Runnable
{
    /** Socket for client to connect to */
    protected Socket clientSocket = null;

    /**
     * Constructor of this class.
     * Use for initialize member data of this class
     * such as socket object.
     *
     * @param clientSocket socket that client connected already
     */
    public ClientHandler(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    /**
     * Method that override from the java.lang.Runnable that
     * will call this function suddenly after constructor are called.
     * This method does about the half of the server does by receiving the
     * packet from the client, Analyze the packet and perform the corresponding
     * operation and then send the response to the client.
     */
    @Override
    public void run()
    {
        try
        {
            /* Declare and initialize the inboxMessage and outboxMessage */
            ArrayList<Message> inboxMessage = null;
            ArrayList<Message> outboxMessage = null;

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
                    /* Perform the login operation */
                    isSuccessFlag = AuthHandler.login(inputPacket.getUsername(), inputPacket.getPassword());

                    /* if login success */
                    if (isSuccessFlag)
                        System.out.println("Login successfully ..");
                    else
                        System.out.println("Login failed ..");
                    break;

                case "register":
                    /* Perform the register operation */
                    isSuccessFlag = AuthHandler.register(inputPacket.getUsername(), inputPacket.getPassword());

                    /* If register success */
                    if (isSuccessFlag)
                        System.out.println("Register successfully ..");
                    else
                        System.out.println("Register failed ..");
                    break;

                case "sendMessage":
                    /* Perform the send message operation in both sender and recipient messageCollection */
                    isSuccessFlag = MessageHandler.send(inputPacket.getMessage(), inputPacket.getMessage().getToAddress(), "inbox");
                    if (isSuccessFlag)
                        isSuccessFlag = MessageHandler.send(inputPacket.getMessage(), inputPacket.getMessage().getFromAddress(), "outbox");

                    /* If sendMessage success */
                    if (isSuccessFlag)
                        System.out.println("Send message successfully ..");
                    else
                        System.out.println("Send message failed ..");
                    break;

                case "replyMessage":

                    String username = inputPacket.getUsername();

                    /* First search the fromAddress's outbox */
                    isSuccessFlag = MessageHandler.reply(inputPacket.getMessage(), username, inputPacket.getMessage().getFromAddress(), "outbox");

                    /* If file not found then search in the inbox instead */
                    if (!isSuccessFlag)
                        isSuccessFlag = MessageHandler.reply(inputPacket.getMessage(), username, inputPacket.getMessage().getFromAddress(), "inbox");

                    /* Second search the toAddress's inbox */
                    isSuccessFlag = MessageHandler.reply(inputPacket.getMessage(), username, inputPacket.getMessage().getToAddress(), "inbox");

                    /* If file not found then search in the outbox instead */
                    if (!isSuccessFlag)
                        isSuccessFlag = MessageHandler.reply(inputPacket.getMessage(), username, inputPacket.getMessage().getToAddress(), "outbox");

                    /* If everything is ok then log the successful message */
                    if (isSuccessFlag)
                        System.out.println("Reply message successfully ..");
                    else
                        System.out.println("Reply message failed ..");

                    break;

                case "deleteMessage":
                    /* Perform delete message operation in both inbox and outbox of recipient and sender */
                    isSuccessFlag = MessageHandler.delete(inputPacket.getMessage(), inputPacket.getMessage().getFromAddress(), "outbox");
                    if (isSuccessFlag)
                        isSuccessFlag = MessageHandler.delete(inputPacket.getMessage(), inputPacket.getMessage().getToAddress(), "inbox");

                    /* If delete message success */
                    if (isSuccessFlag)
                        System.out.println("Delete message successfully ..");
                    else
                         System.out.println("Delete message failed ..");
                    break;

                case "getMessage":
                    /* Perform get message operation in both inbox and outbox of the client */
                    outboxMessage = MessageHandler.getMessage(inputPacket.getUsername(), "outbox");
                    inboxMessage = MessageHandler.getMessage(inputPacket.getUsername(), "inbox");
                    break;

                default:
                    /* If the command invalid */
                    isSuccessFlag = false;
                    System.out.println("Invalid request ..");
                    break;
            }

            /* Create output packet */
            /* Other parameter is set to null because client don't actually need it */
            Packet outPacket = new Packet().setInboxMessage(inboxMessage).setOutboxMessage(outboxMessage).setIsSuccess(isSuccessFlag);

            /* write the objectOutputStream to send the response packet to the client */
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
