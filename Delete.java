package alter;
import java.io.File;

public class Delete implements Runnable
{
    File file;

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public Delete(File file) 
    {
        this.file = file;
    }

    public void delfichier() throws Exception
    {
        File nvrep=this.file;
        
                try
                    {
                        if(nvrep.isDirectory() && nvrep.delete())
                        {
                    
                        }   
                        else
                        {
                            String[] contenue=nvrep.list();
                            if(contenue != null)
                            {
                                for (String file2 : contenue) 
                                {
                                    File file=new File(nvrep,file2);
                                    file.deleteOnExit(); 
                                }
                            }
                            nvrep.delete();
                            
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
}

    @Override
    public void run() 
    {
        try 
        {
            System.out.println("deletion has started");
            this.delfichier();    
        } 
        catch (Exception e) 
        {
                e.printStackTrace();
        }
    }
}
