
/**
 * FileManager.java
 * directly contact with file system.
 * do some opearion like open or read/write the file
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class FileManager
{
    /* Reader object to access the file */
    // private BufferedReader reader = null;
    private Scanner reader = null;
    private BufferedWriter writer = null;

    public boolean create(String filepath)
    {
        File file = new File(filepath);
        System.out.println("checking file at " + file.getAbsolutePath());
        boolean bOk;
        try
        {
            if (file.isFile())
                bOk = false;
            else
                bOk = file.createNewFile();
            System.out.println("create file successfully ..");
        }
        catch (IOException e)
        {
            bOk = false;
        }
        return bOk;
    }

    /**
     * Open a text file, if possible. It will be closed when we open a new file, or
     * get to the end of the old one.
     *
     * @param filename File to open
     * @return true if successfully opened, false if not found.
     * @throws IOException
     */
    public boolean open(String filename) throws IOException
    {

        boolean bOk = true;
        System.out.println("Try opening a file at " + filename);
        try
        {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        }
        catch (IOException io)
        {
            reader = null;
            writer = null;
        }
        try
        {
            // reader = new BufferedReader(new FileReader(filename));
            reader = new Scanner(new File(filename));
            writer = new BufferedWriter(new FileWriter(filename));
            System.out.println("Open file successfully ..");
        }
        catch (FileNotFoundException fnf)
        {
            bOk = false;
            reader = null;
            writer = null;
        }
        return bOk;
    }

    /**
     * Try to read a line from the open file.
     *
     * @return Line as a string, or null if an error occurred.
     */
    public String getNextLine()
    {
        String lineRead = null;
        System.out.println("Reading the file ..");
        if (reader != null) /* if reader is null, file is not open */
        {
            try
            {
                // System.out.println("reader : " + reader.hasNextLine());
                if (reader.hasNextLine())
                {
                    lineRead = reader.nextLine();
                }
                System.out.println("lineRead : " + lineRead);
                if (lineRead == null) /* end of the file */
                {
                    reader.close();
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        /* end if reader not null */
        System.out.println("Reading successful ..");
        return lineRead;
    }

    /**
     * Try to write a line from the open file.
     *
     * @param text text want to write to file
     * @return write success flag
     */
    public boolean setNextLine(String text)
    {
        boolean writeSuccess = true;
        try
        {
            /* if reader is null, file is not open */
            if (writer != null)
            {
                writer.write(text);
                writer.newLine();
                writeSuccess = true;
            }
        }
        catch (IOException ioe)
        {
            writeSuccess = false;
        }
        return writeSuccess;
    }

    public boolean delete(String filepath)
    {
        boolean deleteSuccess = false;
        System.out.println("Try delete a file at " + filepath);
        File file = new File(filepath);
        try
        {
            deleteSuccess = file.delete();
        }
        catch (Exception e)
        {
            deleteSuccess = false;
        }
        System.out.println("Delete file successfully ..");
        return  deleteSuccess;
    }

    /**
     * Explicitly close the reader to free resources
     */
    public void close()
    {
        try
        {
            reader.close();
            writer.close();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);
        }
    }

}
