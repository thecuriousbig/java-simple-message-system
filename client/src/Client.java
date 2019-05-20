import java.io.IOException;

/**
 *  Client class is for represent identity of client user
 *  in the email system
 *
 *  Create by Manchuporn Pungtippimanchai (SaiMai) 59070501060
 *  and Tanatorn Nateesanprasert (Big) 59070501035
 * */
public class Client
{
    /* Name of the client user*/
    private String username;

    /* Password of this client user*/
    private String password;

    /* Inbox is a received message collection*/
    private MessageCollection inbox;

    /**
     *  Constructor of singleton class
     * */
    public Client(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.inbox = new MessageCollection();
    }

    /**
     * Create new message
     * @return true if create successful and send,
     *          false if cancel.
     * */
    public void createMessage()
    {
        Editor.createMessage(username);
    }

    /**
     *  Get username of this client user
     *  @return client's username
     * */
    public String getUsername()
    {
        return username;
    }

    /**
     * Set username of this client user
     *
     * @param username name of this user
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * get password of this client user
     *
     * @return client's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password of this client user
     *
     * @param password of this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *  Get all received messages of this client user
     *  @return message collection of received message
     * */
    public MessageCollection getInbox()
    {
        return inbox;
    }

    /**
     * Assign inbox of this client
     *
     * @param messages message collection of receive message.
     */
    public void setInbox(MessageCollection messages)
    {
        inbox = messages;
    }

    /**
     *  forward a selected message
     *  @param message message that are going to be forwarded by this client to other client
     * */
    public void forwardMessage(Message message)
    {
        if (Editor.createForwardMessage(message, this.getUsername()))
            System.out.println("forward message success");
        else
            System.out.println("forward message failed");
    }

    /**
     * reply a selected message
     *
     * @param message message that will have a reply message
     */
    public void replyMessage(Message message)
    {
        if (Editor.createReplyMessage(message, this.getUsername()))
            System.out.println("reply message success");
        else
            System.out.println("reply message failed");
    }

    /**
     * delete a selected message
     *
     * @param message message that want to be deleted or remove from the collection
     */
    public void deleteMessage(Message message)
    {
    }

    /**
     * Update and fetch all the messages in the inbox
     */
    public boolean updateInbox()
    {
        /* Create serverHandler */
        ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
        /* Create a packet to send to the server */
        Packet packet = new Packet().setCommand("getMessage").setUsername(username);
        /* connect to server */
        boolean isConnect = serverHandler.connect();
        if (!isConnect)
        {
            System.out.println("cannot connect to server!");
            return false;
        }

        System.out.println("sending .. ");
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
        }
        /* send the message */
        boolean bOk = serverHandler.send(packet);
        if (bOk)
        {
            System.out.println("waiting for response ..");
        }
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
        }

        Packet receivePacket = serverHandler.receive();

        /* if sent message successful */
        if (receivePacket.getIsSuccess())
        {
            System.out.println("Sent message complete");
        }
        serverHandler.close();
        /* update the message collection (inbox) */
        this.getInbox().setMessages(receivePacket.getInboxMessage());
        return receivePacket.getIsSuccess();
    }


    /**
     * Show all receive message in Inbox message
     * */
    public void showInbox()
    {
        boolean bOk = updateInbox();
        if (bOk)
        {
            System.out.println("Get messages success ..");
        }
        if(inbox.getMessagesAmount() == 0)
        {
            System.out.println("No such any message in the inbox.");
        }
        else
        {
            inbox.showAllMessages();
            int index = -1;

            while (index == -1)
            {
                System.out.println("Select one message [1-"+inbox.getMessagesAmount()+"] (enter 0 for back to main menu)");
                index = IOUtils.getInteger("Enter answer > ");
                /* Get the \n that still in the system.in buffer */
                IOUtils.getBareString();
            }
            if (index > 0 && (index-1 < inbox.getMessagesAmount()))
            {
                boolean notValid;
                do
                {
                    notValid = false;
                    /* Get the selected message from the message collection */
                    Message selectedMsg = inbox.getOneMessage(index-1);
                    /* show the detail of message */
                    selectedMsg.showMessage();

                    System.out.println("Select one operation below");
                    System.out.println("1. Forward message");
                    System.out.println("2. Reply message");
                    System.out.println("3. Delete message");
                    System.out.println("0. Back to menu");
                    String option = IOUtils.getString("Enter answer > ");
                    switch (option)
                    {
                        case "1":
                            this.forwardMessage(selectedMsg);
                            break;
                        case "2":
                            this.replyMessage(selectedMsg);
                            break;
                        case "3":
                            this.deleteMessage(selectedMsg);
                            break;
                        case "0":
                            System.out.println("Going back to main menu ..");
                            try
                            {
                                Thread.sleep(1000);
                            }
                            catch (Exception e)
                            {
                            }
                            break;
                        default:
                            notValid = true;
                            break;
                    }
                }
                while (notValid);
            }
        }
    }

}
