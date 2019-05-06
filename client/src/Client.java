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
    private static MessageCollection inbox;

    /**
     *  Constructor of singleton class
     * */
    public Client(String username, String password)
    {
        this.username = username;
        this.password = password;
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
     *  @return     true if forward successful,
     *              false if cancel.
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
     * @return true if forward successful
     *         false if cancel.
     */
    public void replyMessage(Message message)
    {
        if (Editor.createReplyMessage(message, this.getUsername()))
            System.out.println("reply message success");
        else
            System.out.println("reply message failed");
    }

    /**
     * Update and fetch all the messages in the inbox
     */
    public void updateInbox()
    {
        /* Create serverHandler */
        ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
        /* Connect with server */
        serverHandler.connect();

        /* Create a packet to send to the server */
        Packet packet = new Packet().setCommand("getMessage").setUsername(this.username);
        /* send request to server */
        serverHandler.send(packet);
        /* receive messages */
        Packet receivePacket = serverHandler.receive();
        /* update the message collection (inbox) */
        this.getInbox().setMessages(receivePacket.getInboxMessage());
    }


    /**
     * Show all receive message in Inbox message
     * */
    public void showInbox()
    {
        System.out.println("INBOX\n");
        this.updateInbox();
        if(this.getInbox().getMessagesAmount() == 0)
        {
            System.out.println("No such any message in the inbox.");
        }
        else
        {
            this.getInbox().showAllMessages();
            System.out.println("-------------------------------------------");

            int index = -1;

            while (index == -1)
            {
                index = IOUtils.getInteger("Select message by index [1-" + getInbox().getMessagesAmount()
                        +" or 0 for no select] : ");
            }
            if (index > 0 && index < getInbox().getMessagesAmount())
            {
                Message selectedMsg = inbox.getMessages().get(index);
                selectedMsg.showMessage();
                String option = IOUtils.getString("\nSelect option \n [F]FORWARD \n [R]REPLY \n [N]NONE");
                option = option.toUpperCase();
                switch (option)
                {
                    case "F":
                        this.forwardMessage(selectedMsg);
                    case "R":
                        this.replyMessage(selectedMsg);
                    case "N":
                        System.out.println("=============================================");
                }
            }
        }
    }

}
