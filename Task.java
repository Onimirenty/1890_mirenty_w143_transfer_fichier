package alter;

import java.util.Date;
import java.util.TimerTask;
import client.NeoFileChooser;

public class Task extends TimerTask 
{
  NeoFileChooser neo=new NeoFileChooser();

  public NeoFileChooser getNeo() {
    return this.neo;
  }

  public void setNeo(NeoFileChooser neo) {
    this.neo = neo;
  }

  @Override
  public void run() 
  {
        String path=getNeo().detect_path_to_save_into();
        boolean value=getNeo().supr(path+"neo");
        if(value) {System.out.println("succeeeeeeeeeeeeeeees");}
        else {System.out.println("failure");}
  }
}