package net.utcode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Initializer {
    public boolean copyPage(){
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("page/index.html");
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
}
