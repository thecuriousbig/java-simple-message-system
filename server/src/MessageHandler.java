import java.io.File;
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
            fileManager.writeNextLine(message.getSubject());
            fileManager.writeNextLine(message.getToAddress());
            fileManager.writeNextLine(message.getFromAddress());
            fileManager.writeNextLine(message.getBodyMessage());
            fileManager.writeNextLine(message.getDateFormat().format(message.getDeliverDate()));

            int size = message.getReplyMessages().size();

            fileManager.writeNextLine(String.valueOf(size));
            message.getReplyMessages().forEach(replyMessage ->
            {
                fileManager.writeNextLine(replyMessage.getSubject());
                fileManager.writeNextLine(replyMessage.getToAddress());
                fileManager.writeNextLine(replyMessage.getFromAddress());
                fileManager.writeNextLine(replyMessage.getBodyMessage());
                fileManager.writeNextLine(message.getDateFormat().format(message.getDeliverDate()));
            });
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
        return statusFlag;
    }

    public static Message readFile(FileManager fileManager) {
        Message message = new Message();
        int replySize = 0;
        try
        {
            message.setSubject(fileManager.getNextLine())
                    .setToAddress(fileManager.getNextLine())
                    .setFromAddress(fileManager.getNextLine())
                    .setBodyMessage(fileManager.getNextLine())
                    .setDeliverDate(message.getDateFormat().parse(fileManager.getNextLine()));

            replySize = Integer.valueOf(fileManager.getNextLine());
            for (Message replyMessage : message.getReplyMessages())
            {
                replyMessage.setSubject(fileManager.getNextLine())
                            .setToAddress(fileManager.getNextLine())
                            .setFromAddress(fileManager.getNextLine())
                            .setBodyMessage(fileManager.getNextLine())
                            .setDeliverDate(replyMessage.getDateFormat().parse(fileManager.getNextLine()));
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
    public static boolean send(Message message, String username)
    {
        FileManager fileManager = new FileManager();
        boolean statusFlag = false;
        System.out.println(message.getSubject());
        String filepath = "../../database/inbox/"+username+"/"+message.getSubject()+".txt";
        System.out.println(filepath);
        try
        {
            /* create a file */
            statusFlag = fileManager.create(filepath);
            /* open file */
            statusFlag = fileManager.openWrite(filepath);
            if (statusFlag)
                statusFlag = writeFile(message, fileManager);
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
        fileManager.closeWrite();
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
            /* open file for reading */
            statusFlag = fileManager.openRead(filepath);
            if (statusFlag)
            {
                /* read a file first */
                inboxMessage = readFile(fileManager);
                /* Add reply message */
                if (inboxMessage != null)
                    inboxMessage.addReplyMessage(message);
            }
            /* Close read fike */
            fileManager.closeRead();

            /* Open file for writing */
            statusFlag = fileManager.openWrite(filepath);
            if (statusFlag)
            {
                /* Start write a file */
                statusFlag = writeFile(inboxMessage, fileManager);
            }
            /* Close write file */
            fileManager.closeWrite();
        }
        catch (Exception e)
        {
            statusFlag = false;
        }
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
                    fileManager.openRead(filesList[i].getPath());
                    messages.add(readFile(fileManager));
                    fileManager.closeRead();
                }
            }
        }
        catch (Exception e)
        {
        }

        return messages;
    }
}
