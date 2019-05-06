/**
 * Client class is for represent identity of client user in the email system
 *
 * Create by Manchuporn Pungtippimanchai (SaiMai) 59070501060 and Tanatorn
 * Nateesanprasert (Big) 59070501035
 */
public class Client
{
    /* Name of the client user */
    private String username;

    /* Password of this client user */
    private String password;

    /**
     * Constructor of singleton class
     */
    public Client(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Get username of this client user
     *
     * @return client's username
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Set username of this client user
     *
     * @param username name of this user
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Get password of this client user
     *
     * @return password name of this user
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Set username of this client user
     *
     * @param username name of this user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }


}
