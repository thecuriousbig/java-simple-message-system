import java.util.ArrayList;
import java.io.Serializable;
/**
 * Packet.java
 *
 * Datatype of our message system use when the client are about to send some
 * data or object to the server. And so are server.
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 *
 */

public class Packet implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String command; /* command text that describe the action type of this packet */
    private Message message; /* message message that are relate or invoke in this packet */
    private String username; /* username client that send this packet or do this operation */
    private String password; /* password password of the client */
    private ArrayList<Message> inboxMessage; /* inboxMessage list of message in client's inbox */
    private boolean isSuccess; /* isSuccess success flag of the operation */

    public Packet() {
    }

    /**
     * Get the command
     *
     * @return string that describe the action type
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Set the command
     *
     * @param command command that want to be set
     */
    public Packet setCommand(String command) {
        this.command = command;
        return this;
    }

    /**
     * Get the message
     *
     * @return message
     */
    public Message getMessage() {
        return this.message;
    }

    /**
     * Set the message
     *
     * @param message message of some client that will send to other
     */
    public Packet setMessage(Message message) {
        this.message = message;
        return this;
    }

    /**
     * Get the client
     *
     * @return client instance
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the client
     *
     * @param client
     */
    public Packet setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * get the path
     *
     * @return path that point to storage
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the path
     *
     * @param path
     */
    public Packet setPassword(String password) {
        this.password = password;
        return this;
    }

    public ArrayList<Message> getInboxMessage() {
        return this.inboxMessage;
    }

    public Packet setInboxMessage(ArrayList<Message> inboxMessage) {
        this.inboxMessage = inboxMessage;
        return this;
    }

    public boolean getIsSuccess() {
        return this.isSuccess;
    }

    public Packet setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }
}
