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

    /* Instance of this class*/
    private static Client instance = null;

    /**
     *  Constructor of singleton class
     * */
    // TODO: 12-Apr-19 get inbox from database
    private Client()
    {
        /* Nothing here */
        /* Use initialize() for init member data instead */
    }

    /**
     *  Get instance of this class
     *  @return instance of client class
     * */
    public static Client getInstance()
    {
        if (instance == null)
            instance = new Client();
        return instance;
    }

    /**
     * Initialize client again
     * */
    // TODO: 12-Apr-19 get inbox from database
    public void initialize(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Reset client
     * */
    public boolean reset()
    {
        instance = null;
        inbox = null;
        return true;
    }

    /**
     * Create new message
     * @return true if create successful and send,
     *          false if cancel.
     * */
    public boolean createMessage()
    {
        return Editor.createMessage(username);
    }

    /**
     *  forward a selected message
     *  @return     true if forward successful,
     *              false if cancel.
     * */
    public boolean forwardMessage(Message message)
    {
        return Editor.createForwardMessage(message,getUsername());
    }

    /**
     * Show all receive message in Inbox message
     * */
    public void showInbox()
    {
        System.out.println("INBOX\n");
        if(getInbox().getMessagesAmout() == 0)
        {
            System.out.println("No such any message in the inbox.");
        }
        else
        {
            getInbox().showAllMessages();
            System.out.println("-------------------------------------------");

            int index = -1;

            while (index == -1)
            {
                index = IOUtils.getInteger("Select message by index [1-" + getInbox().getMessagesAmout()
                        +" or 0 for no select] : ");
            }
            if (index>0 && index<getInbox().getMessagesAmout())
            {
                Message selectedMsg = inbox.getMessages().get(index);
                selectedMsg.showMessage();
                String option = IOUtils.getString("\nSelect option \n [F]FORWARD \n [R]REPLY \n [N]NONE");
                option = option.toLowerCase();
                switch (option)
                {
                    case "F":
                        Editor.createForwardMessage(selectedMsg,getUsername());

                    case "R":
                        Editor.createReplyMessage(selectedMsg,getUsername());

                    case "N":
                        System.out.println("=============================================");
                }
            }
        }
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
     *  Get all received messages of this client user
     *  @return message collection of received message
     * */
    public MessageCollection getInbox()
    {
        return inbox;
    }

    /**
     *  Set username of this client user
     *  @param username     name of this user
     * */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     *  Assign inbox of this client
     *  @param messages message collection of receive message.
     * */
    public void setInbox(MessageCollection messages)
    {
        inbox = messages;
    }

}
