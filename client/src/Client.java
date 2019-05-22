import java.io.IOException;

import javax.lang.model.util.ElementScanner6;

import org.w3c.dom.css.ElementCSSInlineStyle;

/**
 *  Client class is for represent identity of client user
 *  in the email system
 *
 *  Create by Manchuporn Pungtippimanchai (SaiMai) 59070501060
 *  and Tanatorn Nateesanprasert (Big) 59070501035
 * */
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
     *  Constructor of singleton class
     * */
    public Client(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.inbox = new MessageCollection();
        this.outbox = new MessageCollection();
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
     * Get the inbox which is MessageCollection
     * that keep all message that this client receive
     * the message from the other
     *
     *  @return inbox of this client
     * */
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
     * to the other
     *
     * @return outbox of this client
     */
    public MessageCollection getOutbox()
    {
        return outbox;
    }

    /**
     * Set the value of outbox which is MessageCollection
     * that keep all message that this client sent
     * to the other
     *
     * @param messages collection of message that keep in this client's outbox
     */
    public void setOutbox(MessageCollection messages)
    {
        outbox = messages;
    }

    /**
     *  forward a selected message
     *  @param message message that are going to be forwarded by this client to other client
     * */
    public void forwardMessage(Message message)
    {
        if (Editor.createForwardMessage(message, username))
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
        if (Editor.createReplyMessage(message, username))
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
        /* Create serverHandler */
        ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
        /* Create a packet to send to the server */
        Packet packet = new Packet().setCommand("deleteMessage").setMessage(message);
        /* Connect to server */
        boolean isConnect = serverHandler.connect();
        if (!isConnect)
        {
            System.out.println("cannot connect to server");
        }
        else
        {
            System.out.println("sending request ..");
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception e)
            {
            }
            boolean bOk = serverHandler.send(packet);
            if (bOk)
                System.out.println("wait for response ..");
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

            if (receivePacket.getIsSuccess())
            {
                System.out.println("delete message success ..");
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                }
            }
            else
            {
                System.out.println("delete message failed .. ");
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                }
            }


        }
    }

    /**
     * Update and fetch all the messages in the inbox
     */
    public boolean updateMessageCollection()
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
        else
        {
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
                System.out.println("waiting for response ..");
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
            this.getOutbox().setMessages(receivePacket.getOutboxMessage());
            return receivePacket.getIsSuccess();
        }
    }


    /**
     * Show all receive message in Inbox message
     * */
    public void showMessageCollection(String where)
    {
        boolean bOk = updateMessageCollection();
        if (bOk)
        {
            System.out.println("Get messages success ..");
        }
        if (where.compareTo("inbox") == 0)
        {
            if(inbox.getMessagesAmount() == 0)
            {
                System.out.println("-------------------------------------------");
                System.out.println("|                  INBOX                  |");
                System.out.println("-------------------------------------------");
                System.out.println("No such any message in the inbox.");
                System.out.println("-------------------------------------------");
            }
            else
            {
                System.out.println("-------------------------------------------");
                System.out.println("|                  INBOX                  |");
                System.out.println("-------------------------------------------");
                inbox.showAllMessages();
                System.out.println("-------------------------------------------");
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
        else
        {
            if (outbox.getMessagesAmount() == 0)
            {
                System.out.println("-------------------------------------------");
                System.out.println("|                  OUTBOX                 |");
                System.out.println("-------------------------------------------");
                System.out.println("No such any message in the outbox.");
                System.out.println("-------------------------------------------");
            }
            else
            {
                System.out.println("-------------------------------------------");
                System.out.println("|                  OUTBOX                 |");
                System.out.println("-------------------------------------------");
                outbox.showAllMessages();
                System.out.println("-------------------------------------------");
                int index = -1;

                while (index == -1)
                {
                    System.out.println("Select one message [1-" + outbox.getMessagesAmount() + "] (enter 0 for back to main menu)");
                    index = IOUtils.getInteger("Enter answer > ");
                    /* Get the \n that still in the system.in buffer */
                    IOUtils.getBareString();
                }
                if (index > 0 && (index - 1 < outbox.getMessagesAmount()))
                {
                    boolean notValid;
                    do
                    {
                        notValid = false;
                        /* Get the selected message from the message collection */
                        Message selectedMsg = outbox.getOneMessage(index - 1);
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

}
