import java.util.ArrayList;

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
        ArrayList<String> line = new ArrayList<>();
        String body = "";
        int i=0;
        while (true)
        {
            line.add(IOUtils.getBareString());
            if (line.get(i).compareTo("END") == 0)
            {
                for (int j=0;j<i;j++)
                {
                    if (j == i-1)
                        body += line.get(j);
                    else
                        body += line.get(j)+"\n";
                }
                break;
            }
            i++;
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
        System.out.println("==> Enter Forward message below. Type END to  finish.");
        ArrayList<String> line = new ArrayList<>();
        int i=0;
        while (true)
        {
            line.add(IOUtils.getBareString());
            if (line.get(i).compareTo("END") == 0)
            {
                for (int j=0;j<i;j++)
                    if (j != i-1)
                        replyMsg += line.get(j)+"\n";
                    else
                        replyMsg += line.get(j);
                break;
            }
            i++;
        }
        boolean notValid;
        boolean bOk = true;
        do
        {
            notValid = false;
            System.out.println("\nReply the message  ?");
            System.out.println("===================");
            System.out.println("1. Reply the message");
            System.out.println("2. Cancel");
            String response = IOUtils.getString("Enter answer > ");
            if(response.compareTo("1") == 0)
            {
                /* Create new reply message */
                Message reply = new Message().setToAddress(to).setFromAddress(from).setSubject("[RE]" + msg.getSubject()).setBodyMessage(replyMsg);
                /* Add this reply message to the original message */
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
                /* wait for the server to response */
                Packet receivePacket = serverHandler.receive();
                /* Close the connection socket */
                serverHandler.close();

                bOk = receivePacket.getIsSuccess();
            }
            else if(response.compareTo("2") == 0)
            {
                System.out.println("Going back to menu !!");
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                }

                bOk = false;
            }
            else
            {
                System.out.println("Invalid input !!");
                try
                {
                    Thread.sleep(1000);
                } catch (Exception e)
                {
                }
                notValid = true;
            }
        }
        while (notValid);
        return bOk;
    }

    public static boolean createForwardMessage(Message msg, String username)
    {
        String subject = "";
        if (!msg.getSubject().startsWith("[FWD]"))
            subject = msg.getSubject();
        else
            subject = "[FWD]"+msg.getSubject();
        String from = username;
        String to = IOUtils.getString("==> Forward message to : ");
        System.out.println("==> Enter Forward message below. Type END to  finish.");
        ArrayList<String> line = new ArrayList<>();
        String forwardMsg = "";
        int i=0;
        while (true)
        {
            line.add(IOUtils.getBareString());
            if (line.get(i).compareTo("END") == 0)
            {
                for (int j=0;j<i;j++)
                     forwardMsg += line.get(j)+"\n";
                break;
            }
            i++;
        }
        forwardMsg += "--------Forwarded message--------\n";
        forwardMsg += "From: "+msg.getFromAddress()+"\n";
        forwardMsg += "Date: "+msg.getDeliverDate().toString()+"\n";
        forwardMsg += "Subject: "+msg.getSubject()+"\n";
        forwardMsg += "To: "+msg.getToAddress()+"\n";
        forwardMsg += msg.getBodyMessage();

        while (true)
        {
            System.out.println("\nForward the message  ?");
            System.out.println("===================");
            System.out.println("1. Forward the message");
            System.out.println("2. Cancel");
            String response = IOUtils.getString("Enter answer > ");
            if (response.compareTo("1") == 0)
            {
                Message newMsg = new Message().setToAddress(to).setFromAddress(from).setBodyMessage(forwardMsg).setSubject(subject);
                sendMessage(newMsg, to);
                return true;
            }
            else if (response.compareTo("2") == 0)
                return false;
            else
                System.out.println("Error : Bad input");
        }
    }
}
