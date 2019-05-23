import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

/**
 * Message
 *
 * The Message class that represents the message that contains a lot of information such as <br>
 * toAddress, fromAddress, bodyMessage, subject and more.
 * <p>
 * Created by Tanatorn Nateesanprasert (Big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
 */
public class Message implements Serializable
{

    /** UID for serializable use for deserialize the data */
    private static final long serialVersionUID = 1L;

    /** Subject of this message*/
    private String subject;

    /** Name of user who create message*/
    private String from;

    /** Name of recipient*/
    private String to;

    /** Content of this message*/
    private String bodyMessage;

    /** Reply message of this*/
    private ArrayList<Message> replyMessages;

    /** Date and time when first created */
    private Date createdDate;

    /** Data and time when depart the message*/
    private Date deliverDate;

    /** Date format to represent the format of date object */
    protected DateFormat dateFormat;

    /**
     * Constructor of message class.
     * Use for initialize the value of member data.
     * Such as dateFormat or createdDate of replyMessage.
     */
    public Message()
    {
        this.dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        this.setCreatedDate(new Date());
        this.replyMessages = new ArrayList<Message>();
    }

    /**
     * Method to get the subject of this message.
     *
     * @return The subject of this message.
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Method to set the subject of this message.
     *
     * @param subject the topic of this message.
     * @return This message instance.
     */
    public Message setSubject(String subject)
    {
        this.subject = subject;
        return this;
    }

    /**
     * Method to get the fromAddress of this message.
     *
     * @return the fromAddress of this message.
     */
    public String getFromAddress()
    {
        return from;
    }

    /**
     * Method to set the fromAddress of this message.
     *
     * @param fromAddress The address that sent this message.
     * @return This message instance.
     */
    public Message setFromAddress(String fromAddress)
    {
        this.from = fromAddress;
        return this;
    }

    /**
     * Method to get the toAddress of this message.
     *
     * @return the toAddress of this message.
     */
    public String getToAddress()
    {
        return to;
    }

    /**
     * Method to set the toAddress of this message.
     *
     * @param toAddress The address that this message will sent to.
     * @return This message instance.
     */
    public Message setToAddress(String toAddress)
    {
        this.to = toAddress;
        return this;
    }

    /**
     * Method to get the bodyMessage of this message.
     *
     * @return the body of this message.
     */
    public String getBodyMessage()
    {
        return bodyMessage;
    }

    /**
     * Method to set the body of this message.
     *
     * @param bodyMessage content of this message.
     * @return This message instance.
     */
    public Message setBodyMessage(String bodyMessage)
    {
        this.bodyMessage = bodyMessage;
        return this;
    }

    /**
     * Method to get all the reply messages of this message.
     *
     * @return The arraylist that represent all reply messages of this message.
     */
    public ArrayList<Message> getReplyMessages()
    {
        return replyMessages;
    }

    /**
     * Method to add the reply message to this message.
     *
     * @param replyMessage message that will reply to this message.
     * @return This message instance.
     */
    public Message addReplyMessage(Message replyMessage)
    {
        this.replyMessages.add(replyMessage);
        return this;
    }

    /**
     * Method to get date and time that this message was created.
     *
     * @return date and time in EEE MMM dd HH:mm:ss zzz yyyy format.
     */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     * Method to set the createdDate of this message.
     *
     * @param createdDate The date and time that this message was created.
     * @return This message instance.
     */
    public Message setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * Method to get date and time that this message was departed.
     *
     * @return date and time in EEE MMM dd HH:mm:ss zzz yyyy format.
     */
    public Date getDeliverDate()
    {
        return deliverDate;
    }

    /**
     * Method to set the deliverDate of this message.
     *
     * @param deliverDate the date and time of this message.
     * @return This message instance.
     */
    public Message setDeliverDate(Date deliverDate)
    {
        this.deliverDate = deliverDate;
        return this;
    }

    /**
     * Method to init the deliverDate of this message.
     *
     * @return This message instance.
     */
    public Message initialDeliverDate()
    {
        this.deliverDate = new Date();
        return this;
    }

    /**
     * Method to get the date format for this message.
     *
     * @return date format which is EEE MMM dd HH:mm:ss zzz yyyy format.
     */
    public DateFormat getDateFormat()
    {
        return dateFormat;
    }

    /**
     * Method to show and print all of the member data or information of this message.
     * In the beautiful pattern.
     */
    public void showMessage()
    {
        System.out.println("=================================================");
        System.out.println("[Message created at " + this.createdDate + "]");
        System.out.println("-------------------------------------------------");
        System.out.println("SUBJECT: " + this.subject);
        System.out.println("FROM:    " + this.from);
        System.out.println("TO:      " + this.to);
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println(this.bodyMessage);
        System.out.println("-------------------------------------------------");
        System.out.println("[Message sent at " + this.deliverDate + "]");
        System.out.println("=================================================");
        if (this.replyMessages.size() == 0)
        {
            System.out.println("-------------------------------------------------");
            System.out.println("                     NO REPLY                    ");
            System.out.println("-------------------------------------------------");

        }
        else
        {
            System.out.println("-------------------------------------------------");
            System.out.println("                      REPLY                      ");
            System.out.println("-------------------------------------------------");

            for (Message msg : this.replyMessages)
            {
                System.out.println("=================================================");
                System.out.println("SUBJECT: " + "[RE]" + msg.subject);
                System.out.println(msg.from + " Wrote :");
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
                System.out.println(msg.bodyMessage);
                System.out.println("-------------------------------------------------");
                System.out.println("[Replied at " + msg.deliverDate + "]");
                System.out.println("=================================================");
            }
        }
    }

    /**
     * Override version of toString() method.
     * Show and print all important information of the message.
     */
    @Override
    public String toString()
    {
        return "\nMessage subject: " + subject +
               "\ncreated date: " + createdDate +
               "\ndeliver date: " + deliverDate +
               "\ntoAddress: " + to +
               "\nfromAddress: " + from +
               "\nbodyMessage: " + bodyMessage +
               "Reply (" + replyMessages.size() +
               ")\n";
    }
}
