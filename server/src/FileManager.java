/**
 * FileManager.java
 * directly contact with file system.
 * do some opearion like open or read/write the file
 *
 * Created by Tanatorn Nateesanprasert (big) 59070501035 Manchuporn
 * Pungtippimanchai (mai) 59070501060
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class FileManager
{
    /* Reader object to access the file */
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    /**
     * Create a text file in the filepath that user provide in the parameter.
     * @param filepath Path of the file that will be create at.
     * @return true if file was successfully created. Otherwise it will return false.
     */
    public boolean create(String filepath)
    {
        File file = new File(filepath);
        System.out.println("checking file at " + file.getPath());
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
     * Create the directory that not exist from the parameter
     * @param dir path that want to create the directory
     * @return true if directory is created successfully, otherwise false.
     */
    public boolean createDir(String dir)
    {
        return (new File(dir)).mkdirs();
    }

    /**
     * Open a text file for read, if possible. It will be closed when we open a new
     * file, or get to the end of the old one.
     *
     * @param filename File to open
     * @return true if successfully opened, false if not found.
     */
    public boolean openRead(String filename)
    {
        boolean bOk = true;
        try
        {
            if (reader != null)
                reader.close();
        }
        catch (IOException io)
        {
            reader = null;
        }
        try
        {
            reader = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException fnf)
        {
            bOk = false;
            reader = null;
        }
        return bOk;
    }

    /**
     * Open a text file for write, if possible. It will be closed when we open a new
     * file, or get to the end of the old one.
     *
     * @param filename File to open
     * @return true if successfully opened, false if not found.
     */
    public boolean openWrite(String filename)
    {
        boolean bOk = true;
        try
        {
            if (writer != null)
                writer.close();
        }
        catch (IOException io)
        {
            writer = null;
        }
        try
        {
            writer = new BufferedWriter(new FileWriter(filename));
        }
        catch (IOException ioe)
        {
            bOk = false;
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
        String lineReader = null;
        try
        {
            if (reader != null) /* if reader is null, file is not open */
            {
                lineReader = reader.readLine();
                if (lineReader == null) /* end of the file */
                {
                    reader.close();
                }
            } /* end if reader not null */
        }
        catch (IOException ioe)
        {
            lineReader = null;
        }
        return lineReader;
    }

    /**
     * Try to wirte a line to the open file.
     *
     * @param data Data to write in to file.
     * @return Line as a string, or null if an error occurred.
     */
    public boolean writeNextLine(String data)
    {
        boolean success = true;
        try
        {
            if (writer != null) /* if writer is null, file is not open */
            {
                writer.append(data);
                writer.newLine();
            }
        }
        catch (IOException ioe)
        {
            success = false;
        }
        return success;
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
    public void closeRead()
    {
        try
        {
            reader.close();
        }
        catch (IOException ioe)
        {

        }
    }

    /**
     * Explicitly close the writer to free resources
     */
    public void closeWrite()
    {
        try
        {
            writer.close();
        }
        catch (IOException ioe)
        {
        }
    }
}
