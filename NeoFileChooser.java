package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class NeoFileChooser 
{
    public static boolean Filechaker(String path) //verifie l'etat du fichier
    {
        File bruno =new File(path);
        if(bruno.canExecute() && bruno.canRead() && bruno.canWrite())
        {
            return true;
        }
        return false;
    }
    
    public static File YgetFile() throws Exception
    {
        //ouvre le fichier
        JFileChooser choose=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        //enregistre le fichier
        int res=choose.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION)
        {
            File file=choose.getSelectedFile();
            Boolean check=NeoFileChooser.Filechaker(file.getAbsolutePath());
            if(check)
            {
                System.out.println(file.getAbsolutePath());
                return file;
            }
            else
            {
                throw new Exception("fichier endommager");
            }
        }
        else
        {
            throw new Exception("file Not selected Exception");
        }
        
    }
    public void copierFichier(String nvChemin,File file)
    {
        // File file;
        
        try {
            // file = NeoFileChooser.YgetFile();
            Path path =Paths.get(file.getAbsolutePath());
            byte[] bite=Files.readAllBytes(path);
            //fonction
            try (FileOutputStream out=new FileOutputStream(nvChemin+File.separator+file.getName())) 
            {
                // System.out.println("chemin ="+ nvChemin+file.getName());
                out.write(bite);    
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    public String delExtension(String fichierpointgetName )
    {
        String fnameWext=fichierpointgetName.substring(0,fichierpointgetName.lastIndexOf("."));
        return fnameWext;
    }

    public String chooseFiletoSend() throws Exception
    {
        String rep="";
        File file=NeoFileChooser.YgetFile();
        
        // String strDirpath=file.getParent();
        // System.out.println("repertoire parent => "+strDirpath);
        // String name=this.delExtension(file.getName());
        // System.out.println("nom fichier sans l'extension  => "+name);
        // // File nvrep=new File(strDirpath,"neo"+name);
        // nvrep.mkdir();
        String parent=this.detect_path_to_save_into();
        File nvrep=new File(parent,"neo");
        
        System.out.println("nouveau repertoire => "+nvrep.getAbsolutePath());

        boolean yes=nvrep.mkdirs();
        if(yes)
        {
            System.out.println("repertoire cree");
            /*copier le fichier vers le nouveau dossier */
            copierFichier(nvrep.getAbsolutePath(), file);
            rep=nvrep.getAbsolutePath();
        }
        else
        {
            throw new Exception(" une erreur inattendue c'est declarer ");
        }
        return rep;
    }

    public  String detect_path_to_save_into()
    {
        String path=new String();
        String os=System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        if(os.indexOf("win")>=0)
        {
            File [] roots = File.listRoots() ;
            for (File file : roots) 
            {
                if(file.toString().equalsIgnoreCase("D:"+File.separator))
                {
                    path="D:"+File.separator;
                    return  path;
                }
            }
            for (File file : roots) 
            {
                if(file.toString().equalsIgnoreCase("c:"+File.separator))
                {
                    path="C:"+File.separator;
                    return  path;        
                }
            }
        }
        if(os.indexOf("nux")>=0)
        {
            path=File.separator + "home"+File.separator;
            return path;
        }
        return path;
    }
    public void delfichier(File nvrep) throws Exception
    {

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
                        nvrep.deleteOnExit();
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
    }
    public boolean supr(String emplacement)
    {
        boolean valeur=false;
        try
        {
            File file=new File(emplacement);
            if(file.isDirectory() && file.delete())
            {
                System.out.println("fichier supprimer");
            }
            else
            {
                String[] contenue=file.list();
                if(contenue != null)
                {
                    for (String file2 : contenue) 
                    {
                        File f=new File(file,file2);
                        System.out.println(f.getAbsolutePath()+"  deleted");
                        f.delete(); 
                    }
                    System.out.println("file at "+emplacement+" deleted");
                }
                valeur=file.delete();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return valeur;
    }
    public void  executer_fichier_bat(String pathbat)
    {
        ProcessBuilder pb = new ProcessBuilder(pathbat);
        try {
            Process p = pb.start();
            StringBuilder str = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line + "\n");
            }
            int code = p.waitFor();
            if (code == 0) {
                System.out.println(str);
                System.exit(0);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}                                                                                                                                                                                                                                                                                                                                                                                   

