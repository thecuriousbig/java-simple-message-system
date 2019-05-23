/**
 * MessageCollection
 *
 * the {@code MessageCollection} class that represent the collection of message <br>
 * that the user have (inbox or outbox)
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchi (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */

import java.util.ArrayList;

public class MessageCollection
{
    /** arraylist of message represent the message that are in the inbox or outbox */
    private ArrayList<Message> messages;

    /**
     * Constructor of MessageCollection class.
     * Use for initialize the arraylist of message.
     */
    public MessageCollection()
    {
        this.messages = new ArrayList<Message>();
    }

    /**
     * Method to add the message to the messageCollection.
     *
     * @param message The message that will add to the collection.
     * @return The flag that represent the status of this operation.
     */
    public boolean addMessage(Message message)
    {
        if(!messages.contains(message))
        {
            messages.add(message);
            return true;
        }

        return false;
    }

    /**
     * Method to delete one of its message in the messageCollection.
     *
     * @param message The message that will be deleted.
     * @return The flag that represent the status of this operation.
     */
    public boolean deleteMessage(Message message)
    {
        if(messages.contains(message))
        {
            messages.remove(message);
            return true;
        }

        return false;
    }

    /**
     * Method to print all of its message that are in the messageCollection.
     */
    public void showAllMessages()
    {
        int i = 0;

        for(Message message : messages)
        {
            i++;
            System.out.println(i+") "+ message.getSubject()+" FROM "+message.getFromAddress()+" "+message.getDeliverDate().toString());
        }
    }

    /**
     * Method to get the amount of the messageCollection.
     *
     * @return The amount of messageCollection.
     */
    public int getMessagesAmount()
    {
        return messages.size();
    }

    /**
     * Method to get the messageCollection.
     *
     * @return The MessageCollection.
     */
    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    /**
     * Method to set the value of messageCollection.
     *
     * @param messages the messageCollection that want to set to this collection.
     */
    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    /**
     * Method to get one message from the messageCollection.
     *
     * @param index The index of message that want to get.
     * @return The message that user want to get.
     */
    public Message getOneMessage(int index)
    {
        return this.messages.get(index);
    }
}
