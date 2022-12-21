package frame;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;

import javax.swing.*;

import frame.ReSizeImg;


public class Interface extends JFrame 
{
	
	public Interface () throws Exception
	{		
		System.out.println("success in progres");
    	//frame= new JFrame("FORMULAIRE");        
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(true);
    	setLocationRelativeTo(null);
		Dimension Dx=new Dimension(500,500);
		java.awt.Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int w=screen.width;
		int h=screen.height;
		setSize(Dx);
		setLocation((w-this.getWidth())/2, (h-this.getWidth())/2 );
		setLayout(new BorderLayout());
		// setLayout(new GridLayout(3,2));

		// ReSizeImg size=new ReSizeImg();

		File file=new File(System.clearProperty("user.dir")+File.separator+"assets");
		String absparent=file.getAbsolutePath();
		
		String[] pictures=file.list();
		ReSizeImg.changeSize("assets/Download.png","assets/Download.png",(w/30),(h/30));
		ReSizeImg.changeSize("assets/help.png","assets/help.png",(w/30),(h/30));
		ReSizeImg.changeSize("assets/menu.png","assets/menu.png",(w/30),(h/30));
		ReSizeImg.changeSize("assets/upload.png","assets/upload.png",(w/30),(h/30));
		// for (int i = 0; i < pictures.length; i++) 
		// {
		// 	ReSizeImg.changeSize(absparent+pictures[i],absparent+pictures[i],(w/30),(h/30));
		// }
		this.setVisible(true);
		
	}
	public static void main(String[] args) 	throws Exception
	{
		Interface fn=new Interface();
		Icon Download=new ImageIcon("assets/Download.png");
		Icon upload=new ImageIcon("assets/upload.png");
		Icon menu=new ImageIcon("assets/menu.png");
		Icon help=new ImageIcon("assets/help.png");
		JPanel prime=new JPanel();
		prime.setSize(new Dimension(480,480));
		prime.setLayout(new GridLayout(7,6,10,10));
		prime.setVisible(true);
		JPanel[] panels=new JPanel[42];
		for (int i = 0; i < panels.length; i++) 
		{
			if(i==4)
			{
				prime.add(panels[i]=new JPanel());
				panels[i].add(new JButton(menu));
			}
			else if(i==5)
			{
				prime.add(panels[i]=new JPanel());
				panels[i].add(new JButton(help));
			}
			else if(i==20)
			{
				prime.add(panels[i]=new JPanel());
				panels[i].add(new JButton(Download));
			}
			else if(i==21)
			{
				prime.add(panels[i]=new JPanel());
				panels[i].add(new JButton(upload));
			}
			else
			{
				prime.add(panels[i]=new JPanel());
				/*panels[i].add(new JButton(""+i+""));*/

			}

		}
		fn.add(prime);
	}
}
