package daemon;;

import java.util.Date;
import java.util.TimerTask;

import socket.Serveur;

public class Task extends TimerTask 
{
  Serveur neo=new Serveur();

  public Serveur getNeo() {
    return this.neo;
  }

  public void setNeo(Serveur neo) {
    this.neo = neo;
  }

  @Override
  public void run() 
  {
        String path=getNeo().detect_path_to_save_into();
        boolean value=getNeo().supr(path+"neo");
        if(value) 
        {
          System.out.println("succeeeeeeeeeeeeeeees");
          System.exit(0);
        }
        else {System.out.println("failure");}
  }
}