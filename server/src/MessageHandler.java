/**
 * MessageHandler
 *
 * The {@code MessageHandler} class that represent the class that <br>
 * handle operation that relate to message such as send, forward, reply.
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (mai) 59070501060 <br>
 * On 20-May-2019
 */

import java.io.File;
import java.util.ArrayList;

public class MessageHandler
{

    /**
     * Method to write the file in the specific pattern for reducing
     * duplicate code in other method.
     * This class receive the message and the fileManager as the parameter
     * to perform the writeFile operation.
     *
     * @param message message that will be written to the file by the fileManager.
     * @param fileManager the fileManager that contains the reader and writer which initialize the value already.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean writeFile(Message message, FileManager fileManager)
    {
        boolean statusFlag = true;
        try
        {
            /* Write a line one by one using data to write from the message */
            fileManager.writeNextLine(message.getSubject());
            fileManager.writeNextLine(message.getToAddress());
            fileManager.writeNextLine(message.getFromAddress());
            fileManager.writeNextLine(message.getBodyMessage());
            /* The String "---" represent the END of the bodyMessage because body can have many lines*/
            fileManager.writeNextLine("---");
            /* Write the date with dateformat */
            fileManager.writeNextLine(message.getDateFormat().format(message.getDeliverDate()));

            int size = message.getReplyMessages().size();
            fileManager.writeNextLine(String.valueOf(size));

            /* If there is a reply message then write it to the file */
            if (size != 0)
            {
                /* Write the content of all reply message */
                message.getReplyMessages().forEach(replyMessage ->
                {
                    fileManager.writeNextLine(replyMessage.getSubject());
                    fileManager.writeNextLine(replyMessage.getToAddress());
                    fileManager.writeNextLine(replyMessage.getFromAddress());
                    fileManager.writeNextLine(replyMessage.getBodyMessage());
                    fileManager.writeNextLine("---");
                    fileManager.writeNextLine(message.getDateFormat().format(message.getDeliverDate()));
                });
            }
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
        return statusFlag;
    }

    /**
     * Method to read the file in a specific pattern to reduce the coupling of code
     * in the other method.
     * This method read a file by using {@code FileManager} to help doing the
     * utilities task such as read a file easier.
     *
     * @param fileManager fileManager that are initialized already.
     * @return The message that are mapped from reading file
     */
    public static Message readFile(FileManager fileManager)
    {
        /* Create the new message for mapping data from the content of the file */
        Message message = new Message();
        String body = "";
        int replySize = 0;

        try
        {
            /* Try reading the file */
            message.setSubject(fileManager.getNextLine())
                    .setToAddress(fileManager.getNextLine())
                    .setFromAddress(fileManager.getNextLine());

            /* Initialize the variable that use in reading the bodyMessage */
            int i = 0;
            ArrayList<String> line = new ArrayList<>();

            /* Read the bodyMessage */
            while (true)
            {
                /* Read the line */
                line.add(fileManager.getNextLine());
                /* Compare the line with the String "---" to check if this is the END of the body or not */
                if (line.get(i).compareTo("---") == 0)
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
            /* Set more message information */
            message.setBodyMessage(body)
                   .setDeliverDate(message.getDateFormat().parse(fileManager.getNextLine()));

            /* get the replySize */
            replySize = Integer.valueOf(fileManager.getNextLine());

            /* If there are a reply message */
            if (replySize != 0)
            {
                /* Declare and initialize the variable that use for reading the reply message */
                int l;
                int numberOfReplyLoop = 0;
                String replyBody;
                ArrayList<String> replyLine;

                while (numberOfReplyLoop < replySize)
                {
                    /* Read the content of the reply message one by one */
                    Message replyMessage = new Message().setSubject(fileManager.getNextLine())
                                                        .setToAddress(fileManager.getNextLine())
                                                        .setFromAddress(fileManager.getNextLine());

                    l=0;
                    replyBody = "";
                    replyLine = new ArrayList<>();

                    /* Read the bodyMessage of replyMessage */
                    while (true)
                    {
                        /* Read the line */
                        replyLine.add(fileManager.getNextLine());
                        /* Compare the line to the String "---" to check if this is the END of the reply message's body */
                        if (replyLine.get(l).compareTo("---") == 0)
                        {
                            for (int j = 0; j < l; j++)
                            {
                                if (j == l - 1)
                                    replyBody += replyLine.get(j);
                                else
                                    replyBody += replyLine.get(j) + "\n";
                            }
                            break;
                        }
                        l++;
                    }

                    /* Reset the replyLine */
                    replyLine = null;

                    /* Set the replyMessage content */
                    replyMessage.setBodyMessage(replyBody)
                                .setDeliverDate(replyMessage.getDateFormat().parse(fileManager.getNextLine()));

                    /* Add the replyMessage to the list one by one */
                    message.addReplyMessage(replyMessage);
                    /* Increment the loop variable by one */
                    numberOfReplyLoop++;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Something wrong in getMessage operation !!");
            message = null;
        }

        return message;
    }

    /**
     * Method to send the message from the client to the database in the server.
     * This method have to do the utilities tasks such as read and write the file.
     * By using {@code FileManager} to helps this method do task about file easier.
     *
     * @param message message that client want to sent.
     * @param username Name of the user that sent the message.
     * @param where String that tells where the message are located.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean send(Message message, String username, String where)
    {
        FileManager fileManager = new FileManager();

        /* Initialize the variable use for sending the message */
        boolean statusFlag = false;
        String filepath;

        /* First check if the receipient is exist */
        String userPath = "../../database/user/" + username + ".txt";
        File f = new File(userPath);

        /* If file is not exist then terminate the operation */
        if (!f.exists())
        {
            return false;
        }


        /* If message locate in the inbox */
        if (where.compareTo("inbox") == 0)
            /* Set the filePath to the inbox */
            filepath = "../../database/message-collection/" + username + "/inbox/" + message.getSubject() + ".txt";
        else
            /* Otherwise set the filePath to the outbox */
            filepath = "../../database/message-collection/" + username + "/outbox/" + message.getSubject() + ".txt";

        try
        {
            /* create a file */
            statusFlag = fileManager.create(filepath);

            /* open file */
            statusFlag = fileManager.openWrite(filepath);

            /* If open the file success */
            if (statusFlag)
                /* Write the file */
                statusFlag = writeFile(message, fileManager);
        }
        catch (Exception e)
        {
            statusFlag = false;
        }

        /* Close the file */
        fileManager.closeWrite();
        return statusFlag;
    }

    /**
     * Method to handle the reply message operation by writing and reading the file which
     * is similar to the send message but more complex in number of operation that perform
     * in this method. This method receive the message , replyUser, username, and path that
     * the message are located as the parament.
     *
     * @param message The message that client want to reply to other client.
     * @param replyUser The user that are replying the message to other client.
     * @param username The recipient of this reply the message operation.
     * @param where The path that the message are located.
     * @return The flag that represent the overall status of this operation.
     */
    public static boolean reply(Message message, String replyUser, String username, String where)
    {
        FileManager fileManager = new FileManager();

        Message storedMessage = null;
        boolean statusFlag = false;
        String filepath;

        /* If the message locate in the inbox */
        if (where.compareTo("inbox") == 0)
            /* Set the filepath to inbox */
            filepath = "../../database/message-collection/" + username + "/inbox/" + message.getSubject() + ".txt";
        else
            /* Otherwise set the filepath to outbox */
            filepath = "../../database/message-collection/" + username + "/outbox/" + message.getSubject() + ".txt";

        message.setFromAddress(replyUser);

        try
        {
            /* open file for reading */
            statusFlag = fileManager.openRead(filepath);
            if (statusFlag)
            {
                /* read a file first */
                storedMessage = readFile(fileManager);
                /* Add reply message */
                if (storedMessage != null)
                    storedMessage.addReplyMessage(message);
            }
            /* Close read fike */
            fileManager.closeRead();

            /* Open file for writing */
            statusFlag = fileManager.openWrite(filepath);
            if (statusFlag)
            {
                /* Start write a file */
                statusFlag = writeFile(storedMessage, fileManager);
            }
            /* Close write file */
            fileManager.closeWrite();
        }
        catch (Exception e)
        {
        }
        return statusFlag;
    }

    /**
     * Method to perform the delete message in the messageCollection that stored in the
     * database. By passing message, username and the path that the message are located
     * as the parameter.
     * This method will delete the file that stored the message that client want to delete.
     * This means the message are permanent delete and remove from the messageCollection
     * forever.
     *
     * @param message message that client want to delete.
     * @param username Name of user that are performing delete operation.
     * @param where The path that contains the location of the message.
     * @return boolean status that represent the overall status of this operation.
     */
    public static boolean delete(Message message, String username, String where)
    {
        /* Create the fileManager instance */
        FileManager fileManager = new FileManager();
        String filepath;
        /* If the message locate in the inbox */
        if (where.compareTo("inbox") == 0)
            /* Set the path to the inbox */
            filepath = "../../database/message-collection/" + username + "/inbox/" + message.getSubject() + ".txt";
        else
            /* Otherwise set the path to the outbox */
            filepath = "../../database/message-collection/" + username + "/outbox/" + message.getSubject() + ".txt";

        /* delete the file in selected directory */
        return fileManager.delete(filepath);
    }

    /**
     * Method to perform get the message operation. Passing the username and
     * the path which user want to get the messages.
     * This method perform the get the messages request by reading all of the files
     * that located in the filePath and then mapped into the arraylist of message
     * Then this method will return the arraylist of message.
     *
     * @param username Name of the user that want to get the message.
     * @param where the path that all the messages are located.
     * @return The arrayList of message or messageCollection.
     */
    public static ArrayList<Message> getMessage(String username, String where)
    {
        FileManager fileManager = new FileManager();

        /* initialize the variable */
        ArrayList<Message> messages = new ArrayList<>();
        int messageNumber;
        String filepath;

        /* If user want to check the file in inbox */
        if (where.compareTo("inbox") == 0)
            /* Set the path to inbox */
            filepath = "../../database/message-collection/"+username+"/inbox";
        else
            /* Set the path to outbox */
            filepath = "../../database/message-collection/"+username+"/outbox";

        /* Create the folder */
        final File folder = new File(filepath);
        /* list the files */
        File[] filesList = folder.listFiles();

        try
        {
            /* If there is a file */
            if (filesList != null)
            {
                /* Looping equal number of files times */
                messageNumber = filesList.length;
                for (int i=0; i<messageNumber; i++)
                {
                    System.out.println("file " + i + ": " + filesList[i].getPath());
                    /* Open file */
                    fileManager.openRead(filesList[i].getPath());

                    /* Read the file and add the message that return from the read file operation */
                    messages.add(readFile(fileManager));

                    /* Close the file */
                    fileManager.closeRead();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error occurred during gathering data from database .. ");
        }

        System.out.println("Get message SUCCESS.");

        return messages;
    }
}
