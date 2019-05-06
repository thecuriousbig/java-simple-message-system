import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * MessageHandler.java
 * Handle operation that relate to message such as send, forward, reply
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */

public class MessageHandler
{

    public static boolean writeFile(Message message, FileManager fileManager)
    {
        boolean statusFlag = true;
        try
        {
            fileManager.setNextLine(message.getSubject());
            fileManager.setNextLine(message.getToAddress());
            fileManager.setNextLine(message.getFromAddress());
            fileManager.setNextLine(message.getBodyMessage());
            fileManager.setNextLine(message.getDateFormat().format(message.getDeliverDate()));

            int size = message.getReplyMessages().size();

            fileManager.setNextLine(String.valueOf(size));
            message.getReplyMessages().forEach(replyMessage ->
            {
                fileManager.setNextLine(replyMessage.getSubject());
                fileManager.setNextLine(replyMessage.getToAddress());
                fileManager.setNextLine(replyMessage.getFromAddress());
                fileManager.setNextLine(replyMessage.getBodyMessage());
                fileManager.setNextLine(message.getDateFormat().format(message.getDeliverDate()));
            });
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
        return statusFlag;
    }

    public static Message readFile(FileManager fileManager) {
        Message message;
        try
        {
            message = new Message(
                fileManager.getNextLine(), /* Subject */
                fileManager.getNextLine(), /* To Address */
                fileManager.getNextLine(), /* From Address */
                fileManager.getNextLine() /* BodyMessage */
            );
            message.setDeliverDate(message.getDateFormat().parse(fileManager.getNextLine()));
            int replySize = Integer.valueOf(fileManager.getNextLine());
            for (int i = 0; i < replySize; i++)
            {
                Message replyMessage = new Message(
                    fileManager.getNextLine(),  /* Subject */
                    fileManager.getNextLine(),  /* To Address */
                    fileManager.getNextLine(),  /* From Address */
                    fileManager.getNextLine()   /* BodyMessage */
                );
                replyMessage.setDeliverDate(replyMessage.getDateFormat().parse(fileManager.getNextLine()));
                message.addReplyMessage(replyMessage);
            }
        }
        catch (Exception e)
        {
            message = null;
        }
        return message;
    }

    /**
     * handle send the message operation
     *
     * @param message message from client
     */
    public static boolean send(Message message, String filepath)
    {
        FileManager fileManager = new FileManager();
        boolean statusFlag = false;
        try
        {
            /* create a file */
            statusFlag = fileManager.create(filepath);
            /* open file */
            statusFlag = fileManager.open(filepath);
            if (statusFlag)
                statusFlag = writeFile(message, fileManager);
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
        fileManager.close();
        return statusFlag;
    }

    /**
     * handle reply the message operation
     *
     * @param message message from client
     */
    public static boolean reply(Message message, String filepath)
    {
        FileManager fileManager = new FileManager();
        Message inboxMessage = null;
        boolean statusFlag = false;

        try
        {
            /* open file */
            statusFlag = fileManager.open(filepath);
            if (statusFlag)
            {
                /* read a file first */
                inboxMessage = readFile(fileManager);
                if (inboxMessage != null)
                {
                    /* Add reply message */
                    inboxMessage.addReplyMessage(message);
                    /* Write to database again  */
                    statusFlag = writeFile(inboxMessage, fileManager);
                }
            }
        }
        catch (IOException ioe)
        {
            statusFlag = false;
        }
        fileManager.close();
        return statusFlag;
    }

    /**
     * handle delete the message operation
     *
     * @param message message from client
     */
    public static boolean delete(Message message, String filepath)
    {
        FileManager fileManager = new FileManager();
        return fileManager.delete(filepath);
    }

    /**
     * handle get the message operation
     *
     */
    public static ArrayList<Message> getMessage(String filepath)
    {
        int messageNumber;
        ArrayList<Message> messages = new ArrayList<>();
        FileManager fileManager = new FileManager();
        final File folder = new File(filepath);
        File[] filesList = folder.listFiles();

        try
        {
            if (filesList != null)
            {
                messageNumber = filesList.length;
                for (int i=0; i<messageNumber; i++)
                {
                    /* Open file */
                    fileManager.open(filesList[i].getPath());
                    messages.add(readFile(fileManager));
                    fileManager.close();
                }
            }
        }
        catch (Exception e)
        {
        }

        return messages;
    }
}
