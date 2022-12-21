package daemon;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import socket.Serveur;


public class Daemon extends Serveur
{
    public static void main(String[] args) {

    Daemon te=new Daemon();

    TimerTask timerTask = new Task();
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(timerTask, 0, 2000);
    System.out.println("Lancement execution");
    try 
    {
        Thread.sleep(600000);
    } 
    catch (InterruptedException e) 
    {
        e.printStackTrace();
    }
    timer.cancel();
    

    }
}