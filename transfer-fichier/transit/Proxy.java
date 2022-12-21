package socket;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Proxy extends Serveur {

    public static void main(String[] args) throws Exception {
        Proxy pro = new Proxy();
        ServerSocket ser1 = new ServerSocket(9091);
        ServerSocket ser1Msg = new ServerSocket(9092);

        ServerSocket ser2 = new ServerSocket(9093);
        ServerSocket ser2Msg = new ServerSocket(9094);

        ServerSocket ser3 = new ServerSocket(9095);
        ServerSocket ser3Msg = new ServerSocket(9096);

        String Filepath = pro.chooseFiletoSend();/* fonction de Jfille chooser */
        File file = new File(Filepath);
        String name = file.getName();

        // System.out.println(name);
        byte[] byteOfTheFile = pro.FichierEnbyte(Filepath);
        int len = byteOfTheFile.length;

        byte[][] tab = pro.splitByteArray(byteOfTheFile);

        String n1 = pro.AddStringBetweenNameAndExt(name, "1"),
                n2 = pro.AddStringBetweenNameAndExt(name, "2"),
                n3 = pro.AddStringBetweenNameAndExt(name, "3");

        pro.sendString(ser1Msg, n1 + "," + len);// msg
        pro.sendBytes2(tab[0], ser1);

        pro.sendString(ser2Msg, n2 + "," + len);// msg
        pro.sendBytes2(tab[1], ser2);

        pro.sendString(ser3Msg, n3 + "," + len);// msg
        pro.sendBytes2(tab[2], ser3);

    }

}
