package alter;

import java.io.*;
import java.net.Socket;
import client.*;



public class DirectoryTxr extends NeoFileChooser
{   
    Socket clientSocket = null;
    String srcDir = null;
    String dstDir = null;
    byte[] readBuffer = new byte[1024];
    private InputStream inStream = null;
    private OutputStream outStream = null;
    int state = 0;
    final int permissionReqState = 1;
    final int initialState = 0;
    final int dirHeaderSendState = 2;
    final int fileHeaderSendState = 3;
    final int fileSendState = 4;
    final int finishedState = 5;
    private boolean isLive = false;
    private int numFiles = 0;
    private int filePointer = 0;
    String request = "May I send?";
    String respServer = "Yes,You can";
    String dirResponse = "Directory created...Please send files";
    String fileHeaderRecvd = "File header received ...Send File";
    String fileReceived = "File Received";
    String dirFailedResponse = "Failed";        
    File[] opFileList = null;

    public DirectoryTxr(Socket clientSocket, String srcDir, String dstDir) 
    {    
        try 
        {
            this.clientSocket = clientSocket;
            inStream = clientSocket.getInputStream();
            outStream = clientSocket.getOutputStream();
            isLive = true;
            this.srcDir = srcDir;
            this.dstDir = dstDir;
            state = initialState;
            System.out.println("  o======|| directoryTxr ||===o 1 o=>");
            
            readResponse(); //starting read thread
            /*
             * 
             */
            sendMessage(request);
            /*
             * prend un string et l'envoi dans le flux
             */
            state = permissionReqState;
            }
            catch (IOException io) 
            {
                io.printStackTrace();
            }
    }

    private void sendMessage(String message) 
    {
        System.out.println("  o======|| SendMessage || ===o 3 o=> ");

        try 
        {
            sendBytes(request.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) 
        {
            e.printStackTrace();
        }
    }

        /**
        * Thread to read response from server
        */
    private void readResponse() 
    {
        System.out.println("  o======|| readeResponse || ===o 2 o=>");

        Runnable readRunnable = new Runnable() 
        {
            public void run() 
            {
                while (isLive) 
                {
                    try 
                    {
                        int num = inStream.read(readBuffer);
                        /*
                         * atre:
                         * byte[] readBuffer = new byte[1024];
                         * 
                         */
                        if (num > 0) 
                        {
                            byte[] tempArray = new byte[num];
                            System.arraycopy(readBuffer, 0, tempArray, 0, num);
                            processBytes(tempArray);
                            /*
                             * voir l-224
                             * arg=byte[]
                             * utilise la fonct setResponse:
                             *      
                             */
                        }
                    }                     
                    catch (IOException io) 
                    {
                        io.printStackTrace();
                        isLive = false;
                    }
                }
            }           
        };
        Thread readThread = new Thread(readRunnable);
        readThread.start();
    }

    private void sendDirectoryHeader() 
    {
        File file = new File(srcDir);
        if (file.isDirectory()) 
        {
            try 
            {
                String[] childFiles = file.list();
                numFiles = childFiles.length;
                String dirHeader = "$" + dstDir + "#" + numFiles + "&";
                sendBytes(dirHeader.getBytes("UTF-8"));
                /*
                    envoye le nom du repertoir et le nb de
                    fichiwer dedans vers le flux 
                 */
            } 
            catch (UnsupportedEncodingException en) 
            {
                en.printStackTrace();
            }
        } 
        else 
        {
            System.out.println(srcDir + " is not a valid directory");
        }
    }

    private void sendFile(String dirName) 
    {
        File file = new File(dirName);
        if (file.isDirectory()) 
        {
            File[] opFileList = file.listFiles();
            File opFile = opFileList[0];
            try 
            {
                DataInputStream diStream = new DataInputStream(new FileInputStream(opFile));
                int len = (int) opFile.length();
                /*
                if (len > Integer.MAX_VALUE) {
                System.out.println("Cannot sent ...." + file.getName());
        
                return;
                }
                */
                byte[] fileBytes = new byte[len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) 
                {
                    read = read + numRead;
                }
                sendBytes(fileBytes);
            } 
            catch (FileNotFoundException f) 
            {
                f.printStackTrace();
            }    
            catch (IOException e) 
            {
                e.printStackTrace();
            }

        }
    }

    private void sendHeader(String fileName) 
    {
        try 
        {
            File file = new File(fileName);
            if (file.isDirectory()) return;
            //avoiding child directories to avoid confusion
            //if want we can sent them recursively
            //with proper state transitions
        
            String header = "&" + fileName + "#" + file.length() + "*";
            sendHeader(header);
            sendBytes(header.getBytes("UTF-8"));
        } 
        catch (UnsupportedEncodingException e) 
        {
            e.printStackTrace();
        }
    }
    
    private void sendBytes(byte[] dataBytes) 
    {
        synchronized (clientSocket) 
        {
            if (outStream != null) 
            {
                try 
                {
                    /*
                    1 outputSteam de valeur null en atre 
                    fonction quit l'utilise:
                        l-55 sendMessage
                        l-110 sendDirectoryHeader
                        l-123 sendFile
                        l-162 sendHeader
                    */
                    System.out.println("*___****_***_***_***__sendByte");
                    outStream.write(dataBytes);
                    outStream.flush();
                } 
                catch (IOException io) 
                {
                    io.printStackTrace();
                }
            }
        }
    }

    private void processBytes(byte[] data)
    {
        System.out.println("  o======|| ProcessBytes ||===o 4 o=>");

        try 
        {
                String parsedMessage = new String(data, "UTF-8");
                System.out.println(parsedMessage);/* yes you can*/
                setResponse(parsedMessage);
        } 
        catch (UnsupportedEncodingException u) 
        {
            u.printStackTrace();
        }
    }

    private void setResponse(String message) 
    {
        System.out.println("  o======|| setResponse ||===o 5 o=>");

        if (message.trim().equalsIgnoreCase(respServer) && state == permissionReqState) 
        {
            System.out.println("  o======|| sendDirectoryHeader ||===o 5.1 o=>");
            state = dirHeaderSendState;
            sendDirectoryHeader();
        
        } 
        else if (message.trim().equalsIgnoreCase(dirResponse) && state == dirHeaderSendState) 
        {
            
            state = fileHeaderSendState;
            if (LocateDirectory()) 
            {
                System.out.println("  o======|| LocateDirectory && createAndSendHeade ||===o 5.2 o=>");
                createAndSendHeader();
            } 
            else 
            {
                System.out.println("Vacant or invalid directory");
            }
        } 
        else if (message.trim().equalsIgnoreCase(fileHeaderRecvd) && state == fileHeaderSendState) 
        {
            System.out.println("  o======|| sendfile ||===o 5.3 o=>");

            state = fileSendState;
            sendFile(srcDir);
            /*
                this.supr(srcDir);
            */
            state = finishedState;
        
        } 
        else if (message.trim().equalsIgnoreCase(fileReceived) && state == finishedState) 
        {
            System.out.println(" o======|| succes ||===o 5.4 o=>");
            System.out.println("Successfully sent");
            
            System.exit(0); 
            //closeSocket();

        } 
        else if (message.trim().equalsIgnoreCase(dirFailedResponse)) 
        {
            System.out.println(" o======|| failure ||===o 5.5 o=>");

            System.out.println("Going to exit....Error ");
            System.exit(0);
        }

    }

    private void closeSocket() 
    {
        try 
        {
            clientSocket.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private boolean LocateDirectory() 
    {
        boolean status = false;
        File file = new File(srcDir);
        if (file.isDirectory()) 
        {
            opFileList = file.listFiles();
            /*attribut tableau de file de valeur null */
            if (opFileList.length <= 0) 
            { 
                System.out.println("No files found"); 
            } 
            else 
            { 
                status = true; 
            } 
        } 
        return status; 
    } 
    private void createAndSendHeader() 
    { 
        File opFile = opFileList[filePointer];/*attribut tableau de file de valeur null */
        String header = "&" + opFile.getName() + "#" + opFile.length() + "*"; 
        try 
        { 
            sendBytes(header.getBytes("UTF-8"));
            /* 
            2_send bytes prend un tab de byte en arg,ecrit sur l'output
            et le vide vers le flux   
            voir ligne 182
             */
            filePointer++; 
        } 
        catch (UnsupportedEncodingException e) 
        { 
            e.printStackTrace();
        } 
    } 
}
