/**
 * Client
 *
 * The {@code Client} class represent the user that currently login to the system and <br>
 * can perform many operation such as create the message or view the messageCollection and etc.
 * <p>
 * Created by Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 *           Tanatorn Nateesanprasert (Big) 59070501035 <br>
 * On 20-May-2019
 */

import java.io.IOException;

public class Client
{
    /** Username that use to identify the client */
    private String username;

    /** Password that use to verify the client */
    private String password;

    /** Collection of message that keep all message which other client sent to this client */
    private MessageCollection inbox;

    /** Collection of message that keep all message which this client sent to other client */
    private MessageCollection outbox;

    /**
     * Constructor of Client that use for initialize the value of the member data.
     *
     * @param username username of this client
     * @param password password of this client
     */
    public Client(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.inbox = new MessageCollection();
        this.outbox = new MessageCollection();
    }

    /**
     * Get the value of this client's username.
     *
     * @return the username of this client.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Set the username's value of this client.
     *
     * @param username username that want to set as the username of this client
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Get the value of this client's password.
     *
     * @return the password of this client.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Set the password's value of this client.
     *
     * @param password password that want to set as the password of this client
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Get the inbox which is MessageCollection
     * that keep all message that this client receive
     * the message from the other.
     *
     * @return messageCollection (inbox) of this client.
     */
    public MessageCollection getInbox()
    {
        return inbox;
    }

    /**
     * Set the valus of inbox which is MessageCollection
     * that keep all message that this client receive
     * the message from the other
     *
     * @param messages Collection of message that keep in this client's inbox
     */
    public void setInbox(MessageCollection messages)
    {
        inbox = messages;
    }

    /**
     * Get the outbox which is MessageCollection
     * that keep all message that this client sent
     * to the other.
     *
     * @return messageCollection (outbox) of this client.
     */
    public MessageCollection getOutbox()
    {
        return outbox;
    }

    /**
     * Set the value of outbox which is MessageCollection
     * that keep all message that this client sent
     * to the other.
     *
     * @param messages collection of message that keep in this client's outbox.
     */
    public void setOutbox(MessageCollection messages)
    {
        outbox = messages;
    }

    /**
     * Create the message by using the createMessage() method from the {@code Editor} class.
     * The message that are created. Client can decide to send the message to the other client
     * or cancel (the message that are just created will be delete).
     */
    public void createMessage()
    {
        Editor.createMessage(username);
    }

    /**
     * Client can forward the message from their own MessageCollection by select the message
     * that want to forward. With the help of Editor class that help client perform the operation
     * instead of client do it by itself.
     *
     * @param message message that are going to be forwarded by this client.
     * */
    public void forwardMessage(Message message)
    {
        if (Editor.createForwardMessage(message, username))
            System.out.println("forward message success");
        else
            System.out.println("forward message failed");
    }

    /**
     * Client can reply the message from their own MessageCollection by select one of its
     * message and then reply it by calling Editor to help the client perform the reply message
     * operation. By passing the reply message to the server. The server will operate and return
     * it to the client once the operation is success
     *
     * @param message The message that client want to reply the other client.
     */
    public void replyMessage(Message message)
    {
        if (Editor.createReplyMessage(message, username))
            System.out.println("reply message success");
        else
            System.out.println("reply message failed");
    }

    /**
     * Client can delete its own message in the inbox or outbox by calls this method.
     * To delete the message, Client need to send the delete message request to the server.
     * <br>
     * The packet contains the message that want to be deleted and the delete message command.
     *
     * @param message message that will be deleted or removed from the message collection of this client.
     */
    public void deleteMessage(Message message)
    {
        /* Create a packet to send to the server */
        Packet packet = new Packet().setCommand("deleteMessage").setMessage(message);

        /* Call the ConnectionManager to perform the delete message */
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        /* Delete message operation */
        connectionManager.messageOperation(packet, "delete");
    }

    /**
     * Every time client want to view their inbox or outbox. Client have to connect
     * to the server and send the "get message" request to the server by sending the
     * message packet.
     * <br>
     * And the server will response by return the message collection for the client.
     *
     * @return the flag that represent the status of get message operation.
     */
    public boolean updateMessageCollection()
    {
         try
        {
            /* Create a packet to send to the server */
            Packet messagePacket = new Packet().setCommand("getMessage").setUsername(username);

            /* Get the ConnectionManager instance to perform this operation */
            ConnectionManager connectionManager = ConnectionManager.getInstance();

            /* After calling the messageOperation(). It will return the response packet sent from the server */
            Packet serverPacket = connectionManager.messageOperation(messagePacket, "Update");

            if (serverPacket.getIsSuccess())
            {
                /* Update the messageCollection by using the messageCollection that sent from the server */
                this.inbox.setMessages(serverPacket.getInboxMessage());
                this.outbox.setMessages(serverPacket.getOutboxMessage());
            }
            else
                /* Return false because get the message failed */
                return false;

            return serverPacket.getIsSuccess();
        }
        catch (Exception e)
        {
            System.out.println("Problem Occurred in the server !!");
            return false;
        }
    }

    /**
     * Show the {@code MessageCollection} either inbox or outbox of this client.
     * By calling updateMessageCollection() in this clss to fetch the messageCollection
     * of this client that are stored in the server's database. And use the messageCollection
     * that received from the server to show to the user.
     * @param where Which MessageCollection that user want to view. (Inbox or Outbox)
     */
    public void showMessageCollection(String where)
    {
        /* Update the message collection */
        boolean updateStatus = updateMessageCollection();

        /* If the update operation is successful */
        if (updateStatus)
        {
            int amountOfMessages;           /* amount of the message */
            MessageCollection collection;   /* message collection */

            System.out.println("Get messages success ..");

            /* If client want to view inbox */
            if (where.compareTo("inbox") == 0)
            {
                collection = inbox;
                amountOfMessages = inbox.getMessagesAmount();
            }
            else
            {
                collection = outbox;
                amountOfMessages = outbox.getMessagesAmount();
            }

            /* Check if inbox or outbox is empty or not */
            if(amountOfMessages == 0)
            {
                System.out.println("-------------------------------------------");
                System.out.println("                   " + where.toUpperCase());
                System.out.println("-------------------------------------------");
                System.out.println("No such any message in the " + where);
                System.out.println("-------------------------------------------");
            }
            else
            {
                /* Show all message in the collection */
                System.out.println("-------------------------------------------");
                System.out.println("                   " + where.toUpperCase());
                System.out.println("-------------------------------------------");
                collection.showAllMessages();
                System.out.println("-------------------------------------------");

                int index = -1;

                /* Loop asking user which message that user want to view the full detail */
                while (index == -1)
                {
                    /* Get the user input */
                    System.out.println("Select one message [1-"+collection.getMessagesAmount()+"] (enter 0 for back to main menu)");
                    index = IOUtils.getInteger("Enter answer > ");

                    /* Get the \n that still in the system.in buffer */
                    IOUtils.getBareString();
                }

                /* If user input valid index of message in the collection */
                if (index > 0 && (index-1 < collection.getMessagesAmount()))
                {
                    boolean notValid;

                    do
                    {
                        notValid = false;
                        /* Get the selected message from the message collection */
                        Message selectedMsg = collection.getOneMessage(index-1);

                        selectedMsg.showMessage();

                        /* Get the user input about what operation that user want to do */
                        System.out.println("Select one operation below");
                        System.out.println("1. Forward message");
                        System.out.println("2. Reply message");
                        System.out.println("3. Delete message");
                        System.out.println("0. Back to menu");
                        String option = IOUtils.getString("Enter answer > ");

                        /* Perform operation */
                        switch (option)
                        {
                            /* Forward */
                            case "1":
                                this.forwardMessage(selectedMsg);
                                break;
                            /* Reply */
                            case "2":
                                this.replyMessage(selectedMsg);
                                break;
                            /* Delete */
                            case "3":
                                this.deleteMessage(selectedMsg);
                                break;
                            /* Do nothing */
                            case "0":
                                System.out.println("Going back to main menu ..");
                                break;
                            /* Invalid input */
                            default:
                                System.out.println("Invalid input !!");
                                notValid = true;
                                break;
                        }
                    }
                    while (notValid);
                }
            }
        }
    }
}
