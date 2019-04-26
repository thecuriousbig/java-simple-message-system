import java.util.ArrayList;

public class MessageCollection
{
    private ArrayList<Message> messages;

    public MessageCollection()
    {
        this.messages = new ArrayList<Message>();
    }

    public boolean addMessage(Message msg)
    {
        if(!messages.contains(msg))
        {
            messages.add(msg);
            return true;
        }
        return false;
    }

    public boolean deleteMessage(Message msg)
    {
        if(messages.contains(msg))
        {
            messages.remove(msg);
            return true;
        }
        return false;
    }

    public void showAllMessages()
    {
        int i = 0;
        for(Message msg : messages)
        {
            i++;
            System.out.println(i+"\t\tfrom:"+msg.getFromAddress() + "\t" +
                    msg.getSubject() + "\t" + msg.getDeliverDate());
        }
    }

    public int getMessagesAmout()
    {
        return messages.size();
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public Message getOneMessage(int index)
    {
        return this.messages.get(index);
    }
}
