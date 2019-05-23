/**
 * Editor
 *
 * The {@code Editor} class represents the facade class that <br>
 * help client handle the operation with the message such as <br>
 * send the message, reply the message and forward the message.
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */

import java.util.ArrayList;

public class Editor
{

    /**
     * Create the message by let the user filled all information of message
     * such as toAddress, fromAddress, subject, bodyMessage.
     * This help client do the operation while not directly contact with Message.
     *
     * @param username The username of the client.
     */
    public static void createMessage(String username)
    {
        /* Set the value of message's information from user input */
        String to = IOUtils.getString("==> Enter Recipient email address : ");
        String from = username;
        String subject = IOUtils.getString("==> Enter email subject : ");

        /* Declare the variable for set the bodyMessage */
        ArrayList<String> line = new ArrayList<>();
        String body = "";
        int i=0;

        /* Set the bodyMessage of the message */
        System.out.println("==> Enter message text below. Type END to  finish.");
        while (true)
        {
            /* Get the user input */
            line.add(IOUtils.getBareString());

            /* If user enter "END" its mean the end of bodyMessage */
            if (line.get(i).compareTo("END") == 0)
            {
                for (int j = 0; j < i; j++)
                {
                    if (j == i - 1)
                        body += line.get(j);
                    else
                        body += line.get(j) + "\n";
                }
                break;
            }
            i++;
        }

        /* Preview the Created message */
        System.out.println("-------------------------------------------------");
        System.out.println("                 Created Message                 ");
        System.out.println("-------------------------------------------------");
        System.out.println("=================================================");
        System.out.println("SUBJECT: " + subject);
        System.out.println("FROM:    " + from);
        System.out.println("TO:      " + to);
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println(body);
        System.out.println("=================================================");

        /* Ask user if user want to send this message or not */
        System.out.println("\nSend the message  ?");
        System.out.println("1. Send the message");
        System.out.println("2. Cancel");
        /* Get the user input */
        String response = IOUtils.getString("Enter answer > ");

        /* If user want to send the message */
        if (response.equalsIgnoreCase("1"))
        {
            /* Actually create the message */
            Message message = new Message().setFromAddress(from)
                                           .setToAddress(to)
                                           .setSubject(subject)
                                           .setBodyMessage(body);

            /* Call the sendMessage() method to send the message */
            Editor.sendMessage(message);
        }
    }

    /**
     * Method for set the message's toAddress that define the receipient of this message.
     *
     * @param message The message that want to set the toAddress
     * @param to The address that this message will send to.
     */
    public static void setMessageToAddress(Message message, String to)
    {
        message.setToAddress(to);
    }

    /**
     * Method for set the message's fromAddress that define the sender of this message.
     *
     * @param message The message that want to set the fromAddress
     * @param from The address that sent this message.
     */
    public static void setMessageFromAddress(Message message, String from)
    {
        message.setFromAddress(from);
    }

    /**
     * Method for set the message's subject that define the topic of the message.
     *
     * @param message The message that want to set the subject.
     * @param subject Topic of this message.
     */
    public static void setMessageSubject(Message message, String subject)
    {
        message.setSubject(subject);
    }

    /**
     * Method for set the message's body that describe the content of thos message.
     *
     * @param message The message that want to set the body.
     * @param body The content of the message.
     */
    public static void setMessageBody(Message message, String body)
    {
        message.setBodyMessage(body);
    }

    /**
     * Editor receive the message that client want to send to the server as the parameter.
     * By the help of ConnectionManager that help Editor communicate with serverHandler and
     * allow Editor to send the message for the client.
     *
     * @param message Message that are going to be sent by the client to the server.
     * @return the flag that represent the overall status of send the message status.
     */
    public static boolean sendMessage(Message message)
    {
        try
        {
            /* Packet that contains all of information that enough to perform the send message operation */
            Packet messagePacket = new Packet().setCommand("sendMessage").setMessage(message.initialDeliverDate());

            /* Calling the ConnectionManager getInstance */
            ConnectionManager connectionManager = ConnectionManager.getInstance();

            /* Send the message request by calling messaageOperation in the connectionManager to help client send the message to the server */
            Packet serverPacket = connectionManager.messageOperation(messagePacket, "Sending");

            if (serverPacket.getIsSuccess())
                /* Show the message that sent success */
                messagePacket.getMessage().showMessage();

            return serverPacket.getIsSuccess();
        }
        catch (Exception e)
        {
            System.out.println("Problem occurred in the server !!");
            return false;
        }
    }

    /**
     * Create the reply message of the selected message by letting client filled the
     * information of the reply message and then send that message to the server.
     * With the help of ConnectionManager Editor can communicate with the ServerHandler.
     * The Editor can send the reply message request to the server to make the operation success.
     *
     * @param message The message that client select to be replied
     * @param username The username of this client
     * @return the flag that represent the overall status of reply message operation.
     */
    public static boolean createReplyMessage(Message message, String username)
    {
        try
        {
            /* Declare and initialize the variable */
            String to = "";
            String from = username;
            String replyMessage = "";
            ArrayList<String> line = new ArrayList<>();
            int i=0;

            /* Set the toAddress of this reply message */
            if(message.getFromAddress().compareTo(username) == 0)
                to = message.getToAddress();
            else
                to = message.getFromAddress();

            /* User enter the bodtyMessage of this reply message */
            System.out.println("==> Enter Reply message below. Type END to  finish.");
            while (true)
            {
                /* Get the user input and add to the arraylist */
                line.add(IOUtils.getBareString());

                /* If user input "END" its mean the end of the bodyMessage */
                if (line.get(i).compareTo("END") == 0)
                {
                    for (int j = 0; j < i; j++)
                        if (j != i - 1)
                            replyMessage += line.get(j) + "\n";
                        else
                            replyMessage += line.get(j);
                    break;
                }
                i++;
            }

            /* Declare the variable that use check the validity of user input */
            boolean notValid;
            boolean bOk = true;
            do
            {
                /* Init the boolean that represent the validity of user input to false */
                notValid = false;

                /* Ask the user if user want to reply the message or not */
                System.out.println("\nReply the message?");
                System.out.println("1. Reply the message");
                System.out.println("2. Cancel");
                String response = IOUtils.getString("Enter answer > ");

                /* If user choose to reply the message */
                if(response.compareTo("1") == 0)
                {
                    /* Create new reply message */
                    Message reply = new Message().setToAddress(to)
                                                .setFromAddress(from)
                                                .setSubject(message.getSubject())
                                                .setBodyMessage(replyMessage)
                                                .initialDeliverDate();

                    /* Create the packet that will send to the server */
                    Packet messagePacket = new Packet().setCommand("replyMessage").setUsername(username).setMessage(reply);

                    /* Get the instance of ConnectionManager */
                    ConnectionManager connectionManager = ConnectionManager.getInstance();

                    /* Reply the message by calling messageOperation and receive the server's response */
                    Packet serverPacket = connectionManager.messageOperation(messagePacket, "Reply");

                    bOk = serverPacket.getIsSuccess();
                }
                else if(response.compareTo("2") == 0)
                {
                    /* User do nothing */
                    System.out.println("Going back to menu !!");
                    bOk = false;
                }
                else
                {
                    /* If user enter invalid input */
                    System.out.println("Invalid input !!");
                    notValid = true;
                }
            }
            while (notValid);

            return bOk;
        }
        catch (Exception e)
        {
            System.out.println("Problem occurred in the server !!");
            return false;
        }
    }

    /**
     * Forward the selected message to the other client. This operation
     * allow client to write some forwarded message to the recipient that
     * the client forward the message to.
     *
     * @param message The message that the user want to forward.
     * @param username The recipient that user want to forward the message to.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean createForwardMessage(Message message, String username)
    {
        try
        {
            /* Declare and init the value of the variable */
            String subject = "";
            String from = username;

            /* Check if the message has been forwarded before */
            if (message.getSubject().startsWith("[FWD]"))
                /* Use the same subject */
                subject = message.getSubject();
            else
                /* Add the [FWD] to the subject */
                subject = "[FWD]"+message.getSubject();

            /* Get the user input */
            String to = IOUtils.getString("==> Forward message to : ");

            /* Declare the variable that use for set the bodyMessage */
            int i=0;
            String forwardMessage = "";
            ArrayList<String> line = new ArrayList<>();

            /* Get the body of the forwarded message */
            System.out.println("==> Enter Forward message below. Type END to  finish.");

            /* Looping get the bodyMessage */
            while (true)
            {
                /* Get the user input */
                line.add(IOUtils.getBareString());

                /* If user enter "END" its mean the end of the body */
                if (line.get(i).compareTo("END") == 0)
                {
                    for (int j = 0; j < i; j++)
                    {
                            forwardMessage += line.get(j) + "\n";
                    }
                    break;
                }
                i++;
            }

            /* Add the message as the body of the forwarded message */
            forwardMessage += "--------Forwarded message---------\n";
            forwardMessage += "From: " + message.getFromAddress() + "\n";
            forwardMessage += "Date: " + message.getDeliverDate().toString() + "\n";
            forwardMessage += "Subject: " + message.getSubject() + "\n";
            forwardMessage += "To: " + message.getToAddress() + "\n";
            forwardMessage += message.getBodyMessage();

            /* Loop asking user */
            while (true)
            {
                /* Get the user input */
                System.out.println("\nForward the message?");
                System.out.println("1. Forward the message");
                System.out.println("2. Cancel");
                String response = IOUtils.getString("Enter answer > ");

                /* If user want to forward the message */
                if (response.compareTo("1") == 0)
                {
                    /* Create the message and send it to the server to perform forwarding message */
                    Message newMessage = new Message().setToAddress(to)
                                                .setFromAddress(from)
                                                .setBodyMessage(forwardMessage)
                                                .setSubject(subject);

                    /* Send the message to the server */
                    sendMessage(newMessage);

                    return true;
                }
                /* User do nothing */
                else if (response.compareTo("2") == 0)
                {
                    return false;
                }
                /* User enter invalid input */
                else
                {
                    System.out.println("Error : Bad input");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Problem occurred in the server !!");
            return false;
        }
    }

}
