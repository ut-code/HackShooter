package net.utcode;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Initializer {
    public static boolean overwritePage(){
        try{
            InputStream is = Initializer.class.getClassLoader().getResourceAsStream("page/index.html");
            File appDirFile = new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter/");
            if(!appDirFile.exists()){
                appDirFile.mkdirs();
            }
            Path appDir = Paths.get(appDirFile.toString() + "/index.html");
            if(is != null) {
                Files.copy(is, appDir, StandardCopyOption.REPLACE_EXISTING);
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyPageIfNotExists(){
        try{
            File pageFile = new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter/index.html");
            File appDirFile = new File("C:/Users/" + System.getProperty("user.name") + "/HackShooter");
            if(!appDirFile.exists()){
                appDirFile.mkdirs();
            }
            if(!pageFile.exists()){
                Path appDir = Paths.get(pageFile.toString());
                InputStream is = Initializer.class.getClassLoader().getResourceAsStream("page/index.html");
                if(is != null) {
                    Files.copy(is, appDir, StandardCopyOption.REPLACE_EXISTING);
                    return true;
                } else {
                    return false;
                }
            }
            else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean init(){
        return overwritePage();
    }
}
