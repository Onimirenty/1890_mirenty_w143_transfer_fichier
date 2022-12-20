package socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Aux3 extends Serveur
{
    public static void main(String[] args) throws Exception 
    {
        Aux3 aux=new Aux3();
        Socket clientSocket=new Socket("127.0.0.1",9091);
        Socket clientSocketmsg=new Socket("127.0.0.1",9092);
        String msg=aux.receiveString(clientSocketmsg); 
        // System.out.println(msg);
        String[] tab=msg.split(",");

        int len=Integer.parseInt(tab[1]);
        byte[] buit= aux.receiveByte(clientSocket, len);

        String savePath=aux.createDirectory_to_save_into();
        System.out.println(savePath+"/"+tab[0]);
        File file=new File(savePath+"/"+tab[0]);

        OutputStream out =new FileOutputStream(file);
        out.write(buit);
    }
}
