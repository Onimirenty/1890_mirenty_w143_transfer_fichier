
package frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class ReSizeImg 
{
    public static void changeSize(String inImg, String outImg, int w, int h) throws IOException 
    {
      // lit l'image d'entrée
        File f = new File(inImg);
        BufferedImage inputImage = ImageIO.read(f);
        
      // crée l'image de sortie
        BufferedImage img = new BufferedImage(w, h, inputImage.getType());
        
      // balancer l'image d'entrée à l'image de sortie
        Graphics2D g = img.createGraphics();
        g.drawImage(inputImage, 0, 0, w, h, null);
        g.dispose();
        
      // extrait l'extension du fichier de sortie
        String name = outImg.substring(outImg.lastIndexOf(".") + 1);
        
      // écrit dans le fichier de sortie
        ImageIO.write(img, name, new File(outImg));
    }
    
    public static void main(String[] args) 
    {
        String inImg = "test.jpg";
        String outImg = "test_1200x628.jpg";
        
        try 
        {
            int width = 1200;
            int height = 628;
            ReSizeImg.changeSize(inImg, outImg, width, height);
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
}