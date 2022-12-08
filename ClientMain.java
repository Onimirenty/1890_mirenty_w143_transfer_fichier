package alter;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

import alter.Delete;
import client.NeoFileChooser;
public class ClientMain extends NeoFileChooser
{
    private DirectoryTxr transmitter = null;
    Socket clientSocket = null;
    private boolean connectedStatus = false;
    private String ipAddress;
    String srcPath = null;
    String dstPath = "";

        public ClientMain() {}

        public void setIpAddress(String ip) {
            this.ipAddress = ip;
        }

        public void setSrcPath(String path) {
            this.srcPath = path;
    }

        public void setDstPath(String path) {
            this.dstPath = path;
    }

    private void createConnection() 
    {
        Runnable connectRunnable = new Runnable() 
        {
            public void run() 
            {
                while (!connectedStatus) 
                {
                    try 
                    {
                        clientSocket = new Socket(ipAddress, 3339);
                        connectedStatus = true;
                        transmitter = new DirectoryTxr(clientSocket, srcPath, dstPath);
                    } 
                    catch (IOException io) 
                    {
                        io.printStackTrace();
                    }
                }

            }
        };
        Thread connectionThread = new Thread(connectRunnable);
        connectionThread.start();
    }
    public void sendFichier(String destination) throws Exception
    {
        ClientMain main = new ClientMain();
        main.setIpAddress("localHost");
        String src=this.chooseFiletoSend();
        
        main.setSrcPath(src);
        
        main.setDstPath(destination);
        
        main.createConnection();
    }

    public static void main(String[] args)
    {
        try
        {
            ClientMain main = new ClientMain();
            // main.executer_fichier_bat(System.getProperty("user.dir")+File.separator+"exc.bat");
            main.sendFichier("F:/Aprotos");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
