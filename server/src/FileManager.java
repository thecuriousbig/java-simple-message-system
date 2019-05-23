/**
 * FileManager
 *
 * The {@code FileManager} class are the class that perform the utilities <br>
 * task about file system
 * <p>
 * Created by Tanatorn Nateesanprasert (big) 59070501035 <br>
 *            Manchuporn Pungtippimanchai (SaiMai) 59070501060 <br>
 * On 20-May-2019
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
    /** BufferedReader for reading the file */
    private BufferedReader reader = null;

    /** BufferedWriter for writing the file */
    private BufferedWriter writer = null;

    /**
     * Method to create a text file in the filepath that user provide in the parameter.
     *
     * @param filepath Path of the file that will be create at.
     * @return true if file was successfully created. Otherwise it will return false.
     */
    public boolean create(String filepath)
    {
        /* Create new file that locate in the filepath variable */
        File file = new File(filepath);

        /* Log in the server */
        System.out.println("checking file at " + file.getPath());

        /* Try to create the file */
        boolean bOk;
        try
        {
            /* Check if file is already exist */
            if (file.isFile())
                bOk = false;
            /* If not create new file */
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
     * Method to create the directory that not exist from the parameter.
     *
     * @param dir path that want to create the directory
     * @return true if directory is created successfully, otherwise false.
     */
    public boolean createDir(String dir)
    {
        /* create the directory */
        return (new File(dir)).mkdirs();
    }

    /**
     * Method to open a text file for read, if possible. It will be closed when we open a new
     * file, or get to the end of the old one.
     *
     * @param filename File to open.
     * @return true if successfully opened, false if not found.
     */
    public boolean openRead(String filename)
    {
        boolean bOk = true;
        try
        {
            /* If read is null */
            if (reader != null)
                reader.close();
        }
        catch (IOException io)
        {
            reader = null;
        }
        try
        {
            /* Initialize the bufferedReader with the reader */
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
     * Method to open a text file for write, if possible. It will be closed when we open a new
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
            /* If writer is null */
            if (writer != null)
                writer.close();
        }
        catch (IOException io)
        {
            writer = null;
        }
        try
        {
            /* Initialize the bufferedWriter with the writer */
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
     * Method to read a line from the open file.
     *
     * @return Line as a string, or null if an error occurred.
     */
    public String getNextLine()
    {
        String lineReader = null;
        try
        {
            /* if reader is null, file is not open */
            if (reader != null)
            {
                lineReader = reader.readLine();
                /* end of the file */
                if (lineReader == null)
                {
                    reader.close();
                }
            }
        }
        catch (IOException ioe)
        {
            lineReader = null;
        }
        return lineReader;
    }

    /**
     * Method to wirte a line to the open file.
     *
     * @param data Data to write in to file.
     * @return Line as a string, or null if an error occurred.
     */
    public boolean writeNextLine(String data)
    {
        boolean success = true;
        try
        {
            /* if writer is null, file is not open */
            if (writer != null)
            {
                /* Append the data (write the line below) */
                writer.append(data);
                /* write the new line character */
                writer.newLine();
            }
        }
        catch (IOException ioe)
        {
            success = false;
        }
        return success;
    }

    /**
     * Method to delete the file that located in the filepath.
     *
     * @param filepath path of the file that want to delete.
     * @return the flag that represent the overall status of this operation.
     */
    public boolean delete(String filepath)
    {
        boolean deleteSuccess = false;
        System.out.println("Try delete a file at " + filepath);

        /* Create new file variable located in the filepath */
        File file = new File(filepath);
        try
        {
            /* Try deleting the file */
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
