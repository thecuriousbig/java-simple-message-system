public class Editor
{

    public static void createMessage(String username)
    {
        String to = IOUtils.getString("==> Enter Recipient email address : ");
        // Editor.setMessageToAddress(msg, to);
        String from = username;
        // Editor.setMessageFromAddress(msg, from);
        String subject = IOUtils.getString("==> Enter email subject : ");
        // Editor.setMessageSubject(msg, subject);
        System.out.println("==> Enter message text below. Type END to  finish.");
        String body = "";
        while (true)
        {
            String line = IOUtils.getBareString();
            if (line.compareTo("END") == 0)
                break;
            body = body + line + "\n";
        }
        System.out.println("-------------------------------------------------");
        System.out.println("                 Created Message                 ");
        System.out.println("-------------------------------------------------");
        System.out.println("SUBJECT: " + subject);
        System.out.println("FROM:    " + from);
        System.out.println("TO:      " + to);
        System.out.println("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
        System.out.println(body);
        System.out.println("-------------------------------------------------");
        System.out.println("\nSend the message  ?");
        System.out.println("===================");
        System.out.println("1. Send the message");
        System.out.println("2. Cancel");
        String response = IOUtils.getString("Enter answer > ");
        if (response.equalsIgnoreCase("1"))
        {
            Message msg = new Message().setFromAddress(from).setToAddress(to).setSubject(subject).setBodyMessage(body);
            Editor.sendMessage(msg, to);
        }
    }

    public static void setMessageToAddress(Message msg, String to)
    {
        msg.setToAddress(to);
    }

    public static void setMessageFromAddress(Message msg, String from)
    {
        msg.setFromAddress(from);
    }

    public static void setMessageSubject(Message msg, String subject)
    {
        msg.setSubject(subject);
    }

    public static void setMessageBody(Message msg, String body)
    {
        msg.setBodyMessage(body);
    }

    public static boolean sendMessage(Message msg, String to)
    {
        ServerHandler serverHandler =  new ServerHandler("127.0.0.1", 8080);
        Packet packet = new Packet().setCommand("sendMessage").setUsername(to).setMessage(msg.initialDeliverDate());

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
            Thread.sleep(2000);
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

        Packet receivePacket = serverHandler.receive();
        /* if sent message successful */
        if (receivePacket.getIsSuccess())
        {
            System.out.println("Sent message complete");
        }
        serverHandler.close();
        msg.showMessage();
        return receivePacket.getIsSuccess();
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

        String decision = IOUtils.getString("Send[Y], Cancel[N]: ");
        if(decision.equalsIgnoreCase("Y"))
        {
            Message reply = new Message().setToAddress(to).setFromAddress(from).setSubject("RE:" + msg.getSubject());
            msg.addReplyMessage(reply);

            ServerHandler serverHandler = new ServerHandler("127.0.0.1", 8080);
            Packet packet = new Packet().setCommand("replyMessage").setUsername(msg.getToAddress());

            /* connect to server */
            boolean isConnect = serverHandler.connect();
            if (!isConnect)
            {
                System.out.println("cannot connect to server!");
                return false;
            }

            /* send reply message */
            serverHandler.send(packet);

            Packet receivePacket = serverHandler.receive();
            serverHandler.close();
            return receivePacket.getIsSuccess();
        }
        else if(decision.equalsIgnoreCase("N"))
        {
            System.out.println("cancel operation!");
            return false;
        }
        else
        {
            System.out.println("Error : Bad input");
            return false;
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
        forwardMsg = forwardMsg + "\n\n----------------Forwarded message-----------------\n\n"+ msg.getBodyMessage();

        while (true)
        {
            String decision = IOUtils.getString("Send[Y], Cancel[N]: ");
            if (decision.equalsIgnoreCase("Y"))
            {
                Message newMsg = new Message().setToAddress(to).setFromAddress(from).setBodyMessage(forwardMsg).setSubject(subject);
                sendMessage(newMsg, to);
                return true;
            }
            else if (decision.equalsIgnoreCase("N"))
                return false;
            else
            {
                System.out.println("Error : Bad input");
            }
        }
    }
}
