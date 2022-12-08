import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import client.NeoFileChooser;
import alter.Task;


public class Test extends NeoFileChooser
{
    public static void main(String[] args) {
        // String s="a1b2c3d4e5.mp3";

        // String fnameWext=s.substring(0,s.lastIndexOf("."));

        // String[] d=s.split("m");
        //     System.out.println(fnameWext);
        //     System.out.println(System.getProperty("user.dir"));
        

            // File file=new File(System.getProperty("user.dir"),"Nv_dossier");
            // file.mkdir();
        Test te=new Test();
        // File gu=new File("C:/Users/P15A-143-Yvan/Desktop/New Folder/txt4");
        // String hu="D:/neo";
        // te.supr(hu);
        // System.out.println(System.getProperty("user.dir")+File.separator+"exc.bat");

        // te.executer_fichier_bat(System.getProperty("user.dir")+File.separator+"another.bat");
        
        // TimerTask timerTask = new MaTask();
        // Timer timer = new Timer(true);
        // timer.scheduleAtFixedRate(timerTask, 0, 1000);

    TimerTask timerTask = new Task();
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(timerTask, 0, 2000);
    System.out.println("Lancement execution");
    try 
    {
        Thread.sleep(60000);
    } 
    catch (InterruptedException e) 
    {
        e.printStackTrace();
    }
    timer.cancel();
    

    }
}