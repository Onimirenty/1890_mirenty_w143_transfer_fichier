package socket;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Serveur
{
    private void sendBytes(byte[] dataBytes,OutputStream outStream) 
    {
            if (outStream != null) 
            {
                try 
                {
                    outStream.write(dataBytes);
                    outStream.flush();
                } 
                catch (IOException io) 
                {
                    io.printStackTrace();
                }
            }
    }
    protected void sendBytes2(byte[] dataBytes,ServerSocket serveursocket) throws Exception
    {
        Socket socket=serveursocket.accept(); 
        OutputStream outStream=socket.getOutputStream();
            if (outStream != null) 
            {
                try 
                {
                    outStream.write(dataBytes);
                    outStream.flush();
                } 
                catch (IOException io) 
                {
                    io.printStackTrace();
                }
            }
            System.out.println("transfer finish");
    }

    public void sendStringAsByte(String phrase,OutputStream outStream)
    { 
        byte[] dataBytes=phrase.getBytes();
        if (outStream != null) 
        {
            try 
            {
                outStream.write(dataBytes);
                outStream.flush();
            } 
            catch (IOException io) 
            {
                io.printStackTrace();
            }
        }
    }

    public void sendString(ServerSocket serveurSocket,String msg)
    {
        // ServerSocket serveurSocket ;
        Socket clientSocket ;
        PrintWriter out;
        
        try 
        {
            System.out.println("SERVEUR");

            // serveurSocket = new ServerSocket(port);
            clientSocket = serveurSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            out.println(msg);
            out.flush();
                            
            // out.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public String receiveString(Socket clientSocket) throws IOException
    {
        BufferedReader in;
        in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
        String msg=in.readLine();
        return msg;
    }
    public byte[] receiveByte(Socket clientSocket,int len) throws IOException
    {
        InputStream out=clientSocket.getInputStream();
        DataInputStream dataout=new DataInputStream(out);
        byte[] data=new byte[len];
        dataout.read(data);
        return data;

    }

    public void writeBytesToFileNio(String fileOutput, byte[] bytes) throws IOException 
    {
        Path path = Paths.get(fileOutput);
        Files.write(path, bytes);
    }
    public byte[] FichierEnbyte(String pathString) throws Exception
    {
        //retourne le fichier indiquer par le chemin en byte code
        Path path=Paths.get(pathString);//meme que path string mais de type path
        byte[] bits = Files.readAllBytes(path);//transforme le fichier en byte code 
        return bits;
    }

    public  byte[] joinByteArray(byte[] byte1, byte[] byte2, byte[] byte3) 
    {

        byte[] result = new byte[byte1.length + byte2.length + byte3.length];
    
        System.arraycopy(byte1, 0, result, 0, byte1.length);
        System.arraycopy(byte2, 0, result, byte1.length, byte2.length);
        System.arraycopy(byte3, 0, result, byte1.length + byte2.length, byte3.length);

    
        return result;
    
    }
    
    public byte[][] splitByteArray(byte[] input) {
        
        int len=input.length/3;
        byte[] tab1 = null;
        byte[] tab2 = null;
        byte[] tab3 = null;
        if(input.length %3 == 1)
        {
            tab1 = new byte[len];
            tab2 = new byte[len];
            tab3 = new byte[len+1];
        }
        // else if(input.length %3 == 2 )
        // {
        //     tab1 = new byte[len-1];
        //     tab2 = new byte[len];
        //     tab3 = new byte[len+1];
        // }
        else
        {
            tab1 = new byte[len];
            tab2 = new byte[len];
            tab3 = new byte[len];
        }
        System.out.println("origin="+input.length);
        byte[][] alltab={tab1,tab2,tab3};
        System.out.println(tab1.length +" |2=>"+tab2.length+" |3=>"+tab3.length);
        System.arraycopy(input, 0, tab1, 0, tab1.length);
        System.arraycopy(input, tab1.length, tab2, 0, tab2.length);
        System.arraycopy(input, tab1.length + tab2.length, tab3, 0, tab3.length);
        return alltab;
    
    }
    public static boolean Filechaker(String path) //verifie l'etat du fichier
    {
        File bruno =new File(path);
        if(bruno.canExecute() && bruno.canRead() && bruno.canWrite())
        {
            return true;
        }
        return false;
    }
    
    public static File YgetFile() throws Exception
    {
        //ouvre le fichier
        JFileChooser choose=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //enregistre le fichier
        int res=choose.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION)
        {
            File file=choose.getSelectedFile();

            Boolean check=Filechaker(file.getAbsolutePath());
            if(check)
            {
                System.out.println(file.getAbsolutePath());
                return file;
            }
            else
            {
                throw new Exception("fichier endommager");
            }
        }
        else
        {
            throw new Exception("file Not selected Exception");
        }
        
    }
    public void copierFichier(String nvChemin,File file)
    {
        // File file;
        
        try {
            // file = NeoFileChooser.YgetFile();
            Path path =Paths.get(file.getAbsolutePath());
            byte[] bite=Files.readAllBytes(path);
            //fonction
            try (FileOutputStream out=new FileOutputStream(nvChemin+File.separator+file.getName())) 
            {
                // System.out.println("chemin ="+ nvChemin+file.getName());
                out.write(bite);    
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public String delExtension(String fichierpointgetName )
    {
        String fnameWext=fichierpointgetName.substring(0,fichierpointgetName.lastIndexOf("."));
        return fnameWext;
    }

    public String chooseFiletoSend() throws Exception
    {
        String rep="";
        File file=YgetFile();
        rep=file.getAbsolutePath();
        
        return rep;
    }
    public String createDirectory_to_save_into() throws Exception
    {
        String rep="";
        String parent=this.detect_path_to_save_into();
        File nvrep=new File(parent,"neo");
        
        System.out.println("nouveau repertoire => "+nvrep.getAbsolutePath());

        boolean yes=nvrep.mkdirs();
        if(yes)
        {
            System.out.println("repertoire cree");
            rep=nvrep.getAbsolutePath();
        }
        else
        {
            throw new Exception(" une erreur inattendue c'est declarer ");
        }
        return rep;
    }
    public  String detect_path_to_save_into()
    {
        String path=new String();
        String os=System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        if(os.indexOf("win")>=0)
        {
            File [] roots = File.listRoots() ;
            for (File file : roots) 
            {
                if(file.toString().equalsIgnoreCase("D:"+File.separator))
                {
                    path="D:"+File.separator;
                    return  path;
                }
            }
            for (File file : roots) 
            {
                if(file.toString().equalsIgnoreCase("c:"+File.separator))
                {
                    path="C:"+File.separator;
                    return  path;        
                }
            }
        }
        if(os.indexOf("nux")>=0)
        {
            path=File.separator + "home"+File.separator;
            return path;
        }
        return path;
    }
    public void delfichier(File nvrep) throws Exception
    {

            try
                {
                    if(nvrep.isDirectory() && nvrep.delete())
                    {
                    
                    }   
                    else
                    {
                        String[] contenue=nvrep.list();
                        if(contenue != null)
                        {
                            for (String file2 : contenue) 
                            {
                                File file=new File(nvrep,file2);
                                file.deleteOnExit(); 
                            }
                        }
                        nvrep.deleteOnExit();
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
    }
    public boolean supr(String emplacement)
    {
        boolean valeur=false;
        try
        {
            File file=new File(emplacement);
            if(file.isDirectory() && file.delete())
            {
                System.out.println("fichier supprimer");
            }
            else
            {
                String[] contenue=file.list();
                if(contenue != null)
                {
                    for (String file2 : contenue) 
                    {
                        File f=new File(file,file2);
                        System.out.println(f.getAbsolutePath()+"  deleted");
                        f.delete(); 
                    }
                    System.out.println("file at "+emplacement+" deleted");
                }
                valeur=file.delete();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return valeur;
    }
    public void  executer_fichier_bat(String pathbat)
    {
        ProcessBuilder pb = new ProcessBuilder(pathbat);
        try {
            Process p = pb.start();
            StringBuilder str = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line + "\n");
            }
            int code = p.waitFor();
            if (code == 0) {
                System.out.println(str);
                System.exit(0);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}