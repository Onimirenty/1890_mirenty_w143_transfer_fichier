package socket;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Proxy extends Serveur
{
    

    public static void main(String[] args) throws Exception 
    {
        Proxy popo=new Proxy();
        ServerSocket ser1=new ServerSocket(9091);
        ServerSocket ser1Msg=new ServerSocket(9092);

        ServerSocket ser2=new ServerSocket(9093);
        ServerSocket ser2Msg=new ServerSocket(9094);

        ServerSocket ser3=new ServerSocket(9095);
        ServerSocket ser3Msg=new ServerSocket(9096);

        String Filepath=popo.chooseFiletoSend();
        File file=new File(Filepath);
        String name=file.getName();
        // System.out.println(name);
        byte[] byteOfTheFile=popo.FichierEnbyte(Filepath);
        int len=byteOfTheFile.length;

        byte[][] tab=popo.splitByteArray(byteOfTheFile);

        

        popo.sendString(ser1Msg,name+","+len);//msg
        popo.sendBytes2(tab[0],ser1);

        // popo.sendString(ser2Msg,name+","+len);//msg
        // popo.sendBytes2(tab[1],ser2);

        // popo.sendString(ser3Msg,name+","+len);//msg
        // popo.sendBytes2(tab[2],ser3);

    }
    
}
