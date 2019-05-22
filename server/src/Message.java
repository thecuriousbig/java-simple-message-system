import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Message implements Serializable
{

    private static final long serialVersionUID = 1L;

    /** Date and time when first created */
    private Date createdDate;

    /** Data and time when depart the message*/
    private Date deliverDate;

    /** Name of recipient*/
    private String to;

    /** Name of user who create message*/
    private String from;

    /** Content of this message*/
    private String bodyMessage;

    /** Subject of this message*/
    private String subject;

    /** Reply message of this*/
    private ArrayList<Message> replyMessages;

    /** Date format to represent*/
    protected DateFormat dateFormat;

    /**
     *  Constructor of the message
     * */
    public Message()
    {
        this.dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        this.setCreatedDate(new Date());
        this.replyMessages = new ArrayList<Message>();
    }

    /**
     *  Get to address of this message
     *  @return username who receive this message
     * */
    public String getToAddress()
    {
        return to;
    }

    /**
     *  Get from address of this message
     *  @return username who send this message
     * */
    public String getFromAddress()
    {
        return from;
    }

    /**
     *  Get Subject of the message
     *  @return message's subject
     * */
    public String getSubject()
    {
        return subject;
    }

    /**
     *  Get the body message of this message
     *  @return body message
     * */
    public String getBodyMessage()
    {
        return bodyMessage;
    }

    /**
     *  Get all reply messages in this message.
     *  @return all reply messages
     * */
    public ArrayList<Message> getReplyMessages()
    {
        return replyMessages;
    }

    /**
     *  Get date and time which create message
     *  @return date and time in yyyy/MM/dd HH:mm:ss format
     * */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     *  Get date and time which deliver message
     *  @return date and time in yyyy/MM/dd HH:mm:ss format
     * */
    public Date getDeliverDate()
    {
        return deliverDate;
    }
    /**
     *  Format of Date
     *  @return format yyyy/MM/dd HH:mm:ss
     * */
    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    public Message setToAddress(String to)
    {
        this.to = to;
        return this;
    }

    public Message setFromAddress(String from)
    {
        this.from = from;
        return this;
    }

    public Message setSubject(String subject)
    {
        this.subject = subject;
        return this;
    }

    /**
     * Get the body message of this message
     *
     * @return body message
     */
    public Message setBodyMessage(String bodyMessage)
    {
        this.bodyMessage = bodyMessage;
        return this;
    }

    /**
     *  Add new reply message to this message
     *  @param replyMessage     the message that you would like to reply
     * */
    public Message addReplyMessage(Message replyMessage)
    {
        this.replyMessages.add(replyMessage);
        return this;
    }

    public Message setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
        return this;
    }

    public Message initialDeliverDate()
    {
        this.deliverDate = new Date();
        return this;
    }

    public Message setDeliverDate(Date deliverDate)
    {
        this.deliverDate = deliverDate;
        return this;
    }

    /**
     * Show information of this message
     */
    @Override
    public String toString()
    {
        return "\nMessage subject: " + subject + "\ncreated date: " + createdDate + "\ndeliver date: " + deliverDate
               + "\ntoAddress: " + to + "\nfromAddress: " + from + "\nbodyMessage: " + bodyMessage
               + "Reply (" + replyMessages.size() + ")\n";
    }
}
