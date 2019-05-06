import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Message
{
    /** Date and time when first created */
    private Date createdDate;

    /** Data and time when depart the message */
    private Date deliverDate;

    /** Name of recipient */
    private String to;

    /** Name of user who create message */
    private String from;

    /** Content of this message */
    private String bodyMessage;

    /** Subject of this message */
    private String subject;

    /** Reply message of this */
    private ArrayList<Message> replyMessages;

    /** Date format to represent */
    protected DateFormat dateFormat;

    /**
     * Constructor of the message
     */
    public Message(String subject, String to, String from, String bodyMessage)
    {
        this.to = to;
        this.from = from;
        this.bodyMessage = bodyMessage;
        this.subject = subject;
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createdDate = new Date();
    }

    public void setBodyMessage(String bodyMessage)
    {
        this.bodyMessage = bodyMessage;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public void setDeliverDate(Date deliverDate)
    {
        this.deliverDate = deliverDate;
    }

    public void setToAddress(String to)
    {
        this.to = to;
    }

    public void setFromAddress(String from)
    {
        this.from = from;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * Add new reply message to this message
     * 
     * @param replyMessage the message that you would like to reply
     */
    public void addReplyMessage(Message replyMessage)
    {
        this.replyMessages.add(replyMessage);
    }

    /**
     * Get date and time which create message
     * 
     * @return date and time in yyyy/MM/dd HH:mm:ss format
     */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     * Get date and time which deliver message
     * 
     * @return date and time in yyyy/MM/dd HH:mm:ss format
     */
    public Date getDeliverDate()
    {
        return deliverDate;
    }

    /**
     * Get to address of this message
     * 
     * @return username who receive this message
     */
    public String getToAddress()
    {
        return to;
    }

    /**
     * Get from address of this message
     * 
     * @return username who send this message
     */
    public String getFromAddress()
    {
        return from;
    }

    /**
     * Get the body message of this message
     * 
     * @return body message
     */
    public String getBodyMessage()
    {
        return bodyMessage;
    }

    /**
     * Get Subject of the message
     * 
     * @return message's subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Get all reply messages in this message.
     * 
     * @return all reply messages
     */
    public ArrayList<Message> getReplyMessages()
    {
        return replyMessages;
    }

    /**
     * Format of Date
     * 
     * @return format yyyy/MM/dd HH:mm:ss
     */
    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    /**
     * Show information of this message
     */
    @Override
    public String toString()
    {
        return "Message subject : " + subject + '\n' + " created date : " + createdDate + " deliver date : "
                + deliverDate + "\n to : '" + to + '\t' + ", from : '" + from + '\n' + " bodyMessage : \n "
                + bodyMessage + '\n' + "Reply (" + replyMessages.size() + ")\n";
    }

    /**
     * Show all content of this message
     */
    public void showMessage()
    {
        System.out.println("--------------------------------------------");
        System.out.println(getSubject() + "\n");
        System.out.println("from : " + getFromAddress() + "\t\t" + getDeliverDate());
        System.out.println("to : " + getToAddress());
        System.out.println("\n" + getBodyMessage() + "\n");

        for (Message msg : replyMessages)
        {
            System.out.println("\tfrom : " + msg.getFromAddress() + "\t\t" + msg.getDeliverDate());
            System.out.println("\n\t" + msg.getBodyMessage());
            System.out.println("\t--------------------------------------");
        }
    }
}
