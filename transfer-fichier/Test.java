import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import socket.Serveur;

public class Test extends Serveur
{

    public static void main(String[] args) throws Exception 
    {
        Test t=new Test();
        File file=new File("C:/AAA/");
        File[] files=file.listFiles();
        File a=files[0];
        File b=files[1];
        File c=files[2];
        
        byte[] x=t.FichierEnbyte(a.getAbsolutePath());
        byte[] y=t.FichierEnbyte(b.getAbsolutePath());
        byte[] z=t.FichierEnbyte(c.getAbsolutePath());


        byte[] cre=t.joinByteArray(x, y, z) ;
        File full=new File(file.getAbsolutePath()+"/tot2.txt");
        OutputStream out=new FileOutputStream(full);
        out.write(cre);
        // File file2=new File("D:/neo/jujuka.mp4");
        // byte[] buta=t.FichierEnbyte("D:/gf/post malone_-_Goodbye_(Lyrics)(RI).mp4");
        // OutputStream out=new FileOutputStream(file2);
        // out.write(buta);
    
    
    }
}
