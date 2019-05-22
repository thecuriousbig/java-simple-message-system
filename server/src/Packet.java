import java.util.ArrayList;
import java.io.Serializable;
/**
 * Datatype of information that use in this simpleMessageSystem program which have a lot of properties
 * Such as command: command or request message that tells server what client want to do.
 *         message: messages that ase sent along with the packet that have to do some operation such as sending, deleting, etc.
 *         username: username of the client
 *         password: password of the client
 *         inboxMessage: Sometimes server sent the entire messages that client which is inboxMessage.
 *         isSuccess: status flag that descrice how everythings in this request is alright.
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 *
 */

public class Packet implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String command;                     /* command text that describe the action type of this packet */
    private Message message;                    /* message      message that are relate or invoke in this packet */
    private String username;                    /* username     client that send this packet or do this operation */
    private String password;                    /* password     password of the client */
    private ArrayList<Message> inboxMessage;    /* inboxMessage list of message that keep all message that other sent to this client*/
    private ArrayList<Message> outboxMessage;   /* outboxMessage list of message that keep all message that client sent to other */
    private boolean isSuccess;                  /* isSuccess    success flag of the operation */

	/**
     * Get the command that sent along with this packet
     *
     * @return string that describe the action type
     */
    public String getCommand()
    {
        return this.command;
    }

    /**
     * Set the command's value.
     *
     * @param command command that want to be set
     */
    public Packet setCommand(String command)
    {
        this.command = command;
        return this;
    }

    /**
     * Get the message
     *
     * @return message
     */
    public Message getMessage()
    {
        return this.message;
    }

    /**
     * Set the message
     *
     * @param message message of some client that will send to other
     */
    public Packet setMessage(Message message)
    {
        this.message = message;
        return this;
    }

    /**
     * Set the client's username
     *
     * @param username
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Set the client's username
     *
     * @return username of this client
     */
    public Packet setUsername(String username)
    {
        this.username = username;
        return this;
    }

    /**
     * get the password of the client
     *
     * @return password of the client
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Set the password of the client
     *
     * @param password password of the client
     */
    public Packet setPassword(String password)
    {
        this.password = password;
        return this;
    }

    /**
     * Get the inbox messages
     * @return inbox messages
     */
    public ArrayList<Message> getInboxMessage()
    {
        return this.inboxMessage;
    }

    /**
     * Set the inbox messages's value
     * @param inboxMessage inboxMessage that server sent to client
     */
    public Packet setInboxMessage(ArrayList<Message> inboxMessage)
    {
        this.inboxMessage = inboxMessage;
        return this;
    }

    /**
     * Get the outbox messages
     * @return outbox messages
     */
    public ArrayList<Message> getOutboxMessage()
    {
        return this.outboxMessage;
    }

    /**
     * Set the outbox messages's value
     * @param outboxMessage outboxMessage that server sent to client
     */
    public Packet setOutboxMessage(ArrayList<Message> outboxMessage)
    {
        this.outboxMessage = outboxMessage;
        return this;
    }

    /**
     * get the status flag that describe the status of this operation
     * @return true if everythings is alright
     *         false if somethins want wrong
     */
    public boolean getIsSuccess()
    {
        return this.isSuccess;
    }

    /**
     * Set the status flag
     * @param isSuccess
     * @return
     */
    public Packet setIsSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
        return this;
    }

    /**
     * Method to print all the information that this packet have
     * @return string that show all member data.
     */
    @Override
    public String toString()
    {
        return "Packet {command:" + command + ", message:" + message + ", username:" + username + ", password:" + password + "}";
    }
}
