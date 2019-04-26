
public class Editor
{
    public static boolean createMessage(String username)
    {
        return false;
    }

    public static boolean setMessageToAddress(Message msg, String to)
    {
        msg.setToAddress(to);
        return true;
    }

    public static boolean setMessageFromSubject(Message msg, String subject)
    {
        msg.setSubject(subject);
        return true;
    }

    public static boolean setMessageBody(Message msg, String body)
    {
        msg.setBodyMessage(body);
        return true;
    }

    public static boolean sendMessage(Message msg)
    {
        return false;
    }

    public static boolean createReplyMessage(Message msg, String username)
    {
        String to = "";
        String from = username;
        String replyMsg = "";
        if(username == msg.getFromAddress())
        {
            to = msg.getToAddress();
        }
        else
        {
            to = msg.getFromAddress();
        }
        System.out.println("Enter your reply message : ");
        while (true)
        {
            String line = IOUtils.getBareString();
            if (line.compareTo("END") == 0)
                break;
            replyMsg = replyMsg + line + "\n";
        }

        while (true)
        {
            String decision = IOUtils.getString("Send[Y], Cancel[N]: ");
            if(decision.equalsIgnoreCase("Y"))
            {
                Message reply = new Message(to,from,replyMsg,"RE:"+msg.getSubject());
                msg.addReplyMessage(reply);
                return true;
            }
            else if(decision.equalsIgnoreCase("N"))
                return false;
            else
            {
                System.out.println("Error : Bad input");
            }
        }
    }

    public static boolean createForwardMessage(Message msg, String username)
    {
        String forwardMsg = "";
        String to = "";
        String subject = "[FWD]" + msg.getSubject();
        String from = username;
        to = IOUtils.getString("Forward to : ");
        System.out.println("Enter your reply message : ");
        while (true)
        {
            String line = IOUtils.getBareString();
            if (line.compareTo("END") == 0)
                break;
            forwardMsg = forwardMsg + line + "\n";
        }
        forwardMsg = forwardMsg + "\n\n----------------Forwarded message-----------------\n\n"+
                msg.getBodyMessage();

        while (true)
        {
            String decision = IOUtils.getString("Send[Y], Cancel[N]: ");
            if(decision.equalsIgnoreCase("Y"))
            {
                Message newMsg = new Message(to,from,forwardMsg,subject);
                sendMessage(newMsg);
                return true;
            }
            else if(decision.equalsIgnoreCase("N"))
                return false;
            else
            {
                System.out.println("Error : Bad input");
            }
        }

    }
}
