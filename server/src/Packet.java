import java.util.ArrayList;
import java.io.Serializable;

/**
 * Packet
 *
 * The Oacket that represent the datatype of information that use in this simpleMessageSystem
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn <br>
 *            Pungtippimanchai (mai) 59070501060 <br>
 * On 20-May-2019
 */
public class Packet implements Serializable
{
    /** UID for deserialization */
    private static final long serialVersionUID = 1L;

    /** command text that describe the action type of this packet */
    private String command;

    /** message that are relate or invoke in this packet */
    private Message message;

    /** username of the client that send this packet or do this operation */
    private String username;

    /** password of the client */
    private String password;

    /** inboxMessage list of message that keep all message that other sent to this client*/
    private ArrayList<Message> inboxMessage;

    /** outboxMessage list of message that keep all message that client sent to other */
    private ArrayList<Message> outboxMessage;

    /** status flag of the operation */
    private boolean isSuccess;

	/**
     * Method to get the command from this packet.
     *
     * @return string that describe the action type.
     */
    public String getCommand()
    {
        return this.command;
    }

    /**
     * Method to set the command of this packet.
     *
     * @param command command that want to be set.
     * @return This packet instance.
     */
    public Packet setCommand(String command)
    {
        this.command = command;
        return this;
    }

    /**
     * Method to get the message of this packet.
     *
     * @return message of this packet.
     */
    public Message getMessage()
    {
        return this.message;
    }

    /**
     * Method to set the message of this packet.
     *
     * @param message The message that will set to this packet.
     * @return This packet instance.
     */
    public Packet setMessage(Message message)
    {
        this.message = message;
        return this;
    }

    /**
     * Method to get the username of this packet.
     *
     * @return username of this packet.
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Method to set the username of this packet.
     *
     * @param username username that will set to this packet.
     * @return This packet instance.
     */
    public Packet setUsername(String username)
    {
        this.username = username;
        return this;
    }

    /**
     * Method to get the password of the packet.
     *
     * @return password of this packet.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Method to set the password of the packet.
     *
     * @param password password that want to set to this packet.
     * @return This packet istance.
     */
    public Packet setPassword(String password)
    {
        this.password = password;
        return this;
    }

    /**
     * Method to get inbox of this packet.
     *
     * @return ArrayList of message from this packet.
     */
    public ArrayList<Message> getInboxMessage()
    {
        return this.inboxMessage;
    }

    /**
     * Method to set the inboxMessage of the packet.
     *
     * @param inboxMessage inboxMessage that will set in this packet.
     * @return This packet instance.
     */
    public Packet setInboxMessage(ArrayList<Message> inboxMessage)
    {
        this.inboxMessage = inboxMessage;
        return this;
    }

    /**
     * Method to get the outboxMessage of this packet.
     *
     * @return the outboxMessage of this packet.
     */
    public ArrayList<Message> getOutboxMessage()
    {
        return this.outboxMessage;
    }

    /**
     * Method to set the outboxMessage of this packet.
     *
     * @param outboxMessage outboxMessage that user want to set to this packet.
     * @return This packet instance.
     */
    public Packet setOutboxMessage(ArrayList<Message> outboxMessage)
    {
        this.outboxMessage = outboxMessage;
        return this;
    }

    /**
     * Method to get the status flag that describe the status of this operation.
     *
     * @return true if everythings is alright. <br>
     *         false if somethins want wrong.
     */
    public boolean getIsSuccess()
    {
        return this.isSuccess;
    }

    /**
     * Method to Set the status flag that describe that status of this operation.
     *
     * @param isSuccess boolean status that want to set to this packet.
     * @return This packet instance.
     */
    public Packet setIsSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
        return this;
    }

    /**
     * Method to print all the information that this packet have
     *
     * @return string that show all member data.
     */
    @Override
    public String toString()
    {
        return "Packet {command:" + command + ", message:" + message + ", username:" + username + ", password:" + password + "}";
    }
}
